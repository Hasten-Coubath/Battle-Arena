package com.example.character_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BattleConsumer {

    @RabbitListener(queues = "battle-queue")
    public void receiveMessage(String message) {
        // Aqui o código roda sozinho quando a batalha acaba!
        System.out.println("==================================================");
        System.out.println("NOTIFICAÇÃO RECEBIDA DO RABBITMQ:");
        System.out.println(message);
        System.out.println("Logica de atualizar Ranking/XP seria chamada aqui.");
        System.out.println("==================================================");
    }
}