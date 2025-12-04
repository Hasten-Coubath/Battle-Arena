package com.example.character_service.factory;

import com.example.character_service.model.Character;
import com.example.character_service.model.Mage;
import com.example.character_service.model.Warrior;

public class CharacterFactory {

    public static Character create(String type, String name) {
        Character character;
        
        if (type.equalsIgnoreCase("WARRIOR")) {
            character = new Warrior();
        } else if (type.equalsIgnoreCase("MAGE")) {
            character = new Mage();
        } else {
            throw new IllegalArgumentException("Classe inválida: " + type);
        }
        
        character.setName(name);
        return character;
    }
}