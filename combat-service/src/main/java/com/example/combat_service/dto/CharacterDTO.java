package com.example.combat_service.dto;

import lombok.Data;

@Data
public class CharacterDTO {
    private Long id;
    private String name;
    private int hp;
    private int attack;
    private int defense;
    private String characterClass;
}