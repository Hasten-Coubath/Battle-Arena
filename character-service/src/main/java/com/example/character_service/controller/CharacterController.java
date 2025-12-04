package com.example.character_service.controller;

import com.example.character_service.factory.CharacterFactory;
import com.example.character_service.model.Character;
import com.example.character_service.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private CharacterRepository repository;

    @PostMapping
    public ResponseEntity<Character> create(@RequestBody CharacterRequest request) {
        Character newChar = CharacterFactory.create(request.role, request.name);
        return ResponseEntity.ok(repository.save(newChar));
    }

    @GetMapping
    public ResponseEntity<List<Character>> list() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    public record CharacterRequest(String name, String role) {}

    @PostMapping("/{id}/receive-damage")
    public ResponseEntity<Character> receiveDamage(@PathVariable Long id, @RequestParam int damage) {
        Character character = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personagem não encontrado"));

        int currentHp = character.getHp();
        int newHp = currentHp - damage;
        
        if (newHp < 0) newHp = 0;

        character.setHp(newHp);
        return ResponseEntity.ok(repository.save(character));
    }
}