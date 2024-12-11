package com.example.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Bean pour encoder les mots de passe
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Algorithme sécurisé
    }

    // Bean pour la configuration de la sécurité des requêtes HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Désactiver CSRF si non nécessaire
        http.csrf(csrf -> csrf.disable());

        // Configurer les autorisations d'accès
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger accessible sans authentification
                .anyRequest().permitAll() // Permettre toutes les requêtes sans authentification
        );

        // Pas d'authentification ni de gestion de sessions
        http.securityContext(securityContext -> securityContext.requireExplicitSave(false));
        http.sessionManagement(session -> session.disable());

        return http.build();
    }
}
