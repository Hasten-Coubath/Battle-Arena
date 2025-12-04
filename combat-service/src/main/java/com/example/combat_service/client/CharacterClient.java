package com.example.combat_service.client;

import com.example.combat_service.dto.CharacterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam; 

@FeignClient(name = "character-service", url = "http://localhost:8082") 
public interface CharacterClient {
    
    @GetMapping("/characters/{id}")
    CharacterDTO getCharacterById(@PathVariable("id") Long id);

    @PostMapping("/characters/{id}/receive-damage")
    CharacterDTO sendDamage(@PathVariable("id") Long id, @RequestParam("damage") int damage);
}