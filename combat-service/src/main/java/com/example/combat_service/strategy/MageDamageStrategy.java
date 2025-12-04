package com.example.combat_service.strategy;

import com.example.combat_service.dto.CharacterDTO;

public class MageDamageStrategy implements DamageStrategy {
    
    @Override
    public int calculateDamage(CharacterDTO attacker, CharacterDTO defender) {
        int damage = attacker.getAttack() - (defender.getDefense() / 2);
        return Math.max(damage, 1);
    }
}