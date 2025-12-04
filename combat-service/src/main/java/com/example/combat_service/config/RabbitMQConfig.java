package com.example.combat_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue battleQueue() {
        // Cria a fila 'battle-queue' automaticamente se ela não existir
        return new Queue("battle-queue", true);
    }
}