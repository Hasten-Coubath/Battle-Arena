package com.example.combat_service.service;

import com.example.combat_service.client.CharacterClient;
import com.example.combat_service.dto.CharacterDTO;
import com.example.combat_service.strategy.DamageStrategy;
import com.example.combat_service.strategy.MageDamageStrategy;
import com.example.combat_service.strategy.WarriorDamageStrategy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CombatService {

    @Autowired
    private CharacterClient characterClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public String fight(Long attackerId, Long defenderId) {
        CharacterDTO attacker = characterClient.getCharacterById(attackerId);
        CharacterDTO defender = characterClient.getCharacterById(defenderId);

        if (attacker == null || defender == null) return "Erro: Lutador não encontrado!";
        if (defender.getHp() <= 0) return "\n💀 " + defender.getName() + " JÁ ESTÁ MORTO! TENHA PIEDADE! 💀";

        // 1. Estratégia
        DamageStrategy strategy;
        if ("MAGE".equalsIgnoreCase(attacker.getCharacterClass())) {
            strategy = new MageDamageStrategy();
        } else {
            strategy = new WarriorDamageStrategy();
        }

        // 2. Calcula Dano
        int damage = strategy.calculateDamage(attacker, defender);

        // 3. Persiste o Dano (Chama o Character Service)
        CharacterDTO defenderUpdated = characterClient.sendDamage(defenderId, damage);

        // 4. Notifica RabbitMQ
        String msg = String.format("FIM DE ROUND: %s tirou %d HP de %s.", attacker.getName(), damage, defender.getName());
        rabbitTemplate.convertAndSend("battle-queue", msg);

        // 5. --- RETORNO GRÁFICO (A Mágica acontece aqui) ---
        return renderBattleField(attacker, defenderUpdated, damage);
    }

    // --- MÉTODOS VISUAIS ---

    private String renderBattleField(CharacterDTO atk, CharacterDTO def, int dmg) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n==================================================\n");
        sb.append("             ⚔️  BATTLE ARENA  ⚔️             \n");
        sb.append("==================================================\n\n");

        // Lutador 1 (Atacante)
        sb.append(String.format(" %-15s (ATACANTE) 😠\n", atk.getName()));
        sb.append(" " + getIcon(atk.getCharacterClass()) + "\n");
        sb.append(" HP: " + drawHealthBar(atk.getHp()) + " " + atk.getHp() + "\n");
        
        sb.append("\n            💥 POW! -" + dmg + " DANO! 💥\n\n");

        // Lutador 2 (Defensor)
        sb.append(String.format(" %35s (DEFENSOR) 😵\n", def.getName()));
        sb.append(String.format("%40s\n", getIcon(def.getCharacterClass())));
        sb.append(String.format("%40s %d\n", drawHealthBar(def.getHp()), def.getHp()));
        
        sb.append("\n==================================================\n");
        
        if (def.getHp() <= 0) {
            sb.append("          ☠️  FATALITY - VITORIA!  ☠️         \n");
        } else {
            sb.append("               A LUTA CONTINUA...                 \n");
        }
        sb.append("==================================================\n");

        return sb.toString();
    }

    private String getIcon(String role) {
        if ("MAGE".equalsIgnoreCase(role)) return "(∩｀-´)⊃━☆ﾟ.*･｡ﾟ"; // Mago soltando magia
        return "  o()xxxx[{::::::::>"; // Espada do Guerreiro
    }

    private String drawHealthBar(int hp) {
        int maxHp = 100; // Assumindo 100 como base para a barra
        int bars = hp / 10; // Cada 10 de vida = 1 quadrado
        if (bars < 0) bars = 0;
        if (bars > 10) bars = 10;

        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < 10; i++) {
            if (i < bars) bar.append("█"); // Bloco cheio
            else bar.append("░"); // Bloco vazio
        }
        bar.append("]");
        return bar.toString();
    }
}