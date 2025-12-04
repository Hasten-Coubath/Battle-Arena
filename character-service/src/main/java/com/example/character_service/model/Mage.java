package com.example.character_service.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MAGE")
public class Mage extends Character {
    
    public Mage() {
        // Atributos base do Mago (menos vida, mais ataque)
        this.setHp(100);
        this.setAttack(35);
        this.setDefense(5);
    }
}