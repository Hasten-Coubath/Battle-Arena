package com.example.character_service.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("WARRIOR") // O valor que vai pro banco na coluna 'classe_personagem'
public class Warrior extends Character {
    
    public Warrior() {
        // Atributos base do Guerreiro
        this.setHp(150);
        this.setAttack(20);
        this.setDefense(15);
    }
}