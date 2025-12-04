package com.example.character_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "characters")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Todos numa tabela só
@DiscriminatorColumn(name = "classe_personagem", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int hp;
    private int attack;
    private int defense;
    
    // Esse campo é preenchido automaticamente pelo banco (MAGE ou WARRIOR)
    @Column(name = "classe_personagem", insertable = false, updatable = false)
    private String characterClass;
}