package com.example.combat_service.strategy;

import com.example.combat_service.dto.CharacterDTO;
import java.util.Random; // Importar Random

public class WarriorDamageStrategy implements DamageStrategy {
    
    @Override
    public int calculateDamage(CharacterDTO attacker, CharacterDTO defender) {
        int baseDamage = attacker.getAttack() - defender.getDefense();
        if (baseDamage < 1) baseDamage = 1;

        // LÓGICA DE JOGO: 20% de chance de CRÍTICO!
        if (new Random().nextInt(100) < 20) {
            System.out.println(">>> ACERTO CRÍTICO! DANO DOBRADO! <<<");
            return baseDamage * 2;
        }

        return baseDamage;
    }
}