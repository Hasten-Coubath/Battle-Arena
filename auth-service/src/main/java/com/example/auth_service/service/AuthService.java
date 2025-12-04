package com.example.auth_service.service;

import com.example.auth_service.dto.LoginRequest; // Importante
import com.example.auth_service.dto.RegisterRequest;
import com.example.auth_service.model.User;
import com.example.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método de Registro (Já existia)
    public User register(RegisterRequest request) {
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Usuário já existe!");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setRole(request.getRole());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        return repository.save(newUser);
    }

    // --- MÉTODO NOVO QUE ESTAVA FALTANDO ---
    public String login(LoginRequest request) {
        // 1. Busca usuário
        User user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        // 2. Verifica senha
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha incorreta!");
        }

        // 3. Retorna Token Fake
        return "TOKEN-FAKE-DE-ACESSO-" + user.getId();
    }
}