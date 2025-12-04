package com.example.combat_service.controller;

import com.example.combat_service.service.CombatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/combat") 
public class CombatController {

    @Autowired
    private CombatService combatService;

    
    @PostMapping("/attack") 
    public ResponseEntity<String> attack(@RequestParam Long attackerId, @RequestParam Long defenderId) {
        String result = combatService.fight(attackerId, defenderId);
        return ResponseEntity.ok(result);
    }
}