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
        // Désactiver CSRF uniquement si vous n'utilisez pas de sessions ou de formulaires
        http.csrf(csrf -> csrf.disable());

        // Configurer les autorisations d'accès
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()  // Autoriser l'accès à Swagger sans authentification
                //.requestMatchers("/api/users/**").hasRole("ADMIN")  // Restreindre l'accès à /api/users aux utilisateurs ayant le rôle "ADMIN"
                //.requestMatchers("/api/technicians/**").hasRole("TECHNICIEN") // Restreindre l'accès aux techniciens
                .anyRequest().authenticated()  // Autres endpoints nécessitent une authentification
        );

        return http.build();
    }
}
