package com.example.combat_service.strategy;

import com.example.combat_service.dto.CharacterDTO;

public interface DamageStrategy { 
    int calculateDamage(CharacterDTO attacker, CharacterDTO defender);
}