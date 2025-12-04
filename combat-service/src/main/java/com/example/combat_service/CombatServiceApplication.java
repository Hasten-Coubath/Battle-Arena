package com.example.combat_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // <--- Importante

@SpringBootApplication
@EnableFeignClients 
public class CombatServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CombatServiceApplication.class, args);
    }
}