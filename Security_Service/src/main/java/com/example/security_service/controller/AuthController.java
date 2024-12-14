package com.example.security_service.controller;

import com.example.security_service.dto.LoginRequest;
import com.example.security_service.service.SecurityService;
import com.example.security_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final SecurityService securityService;
    private final UserService userService;

    @Autowired
    public AuthController(SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public String authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Vérification de l'email avant l'authentification
        if (userService.getUserByEmail(loginRequest.getEmail()) == null) {
            return "Email not found";
        }

        // Authentification de l'utilisateur et génération du JWT
        String token = securityService.authenticateUser(loginRequest);

        return token;
    }

    @PostMapping("/restricted-action")
    public String restrictedAction(@RequestHeader("Authorization") String token) {
        // Vérification du rôle de l'utilisateur à partir du token
        String requiredRole = "admin"; // Par exemple, seul un admin peut effectuer cette action

        securityService.checkUserRole(token, requiredRole);

        // Action réservée aux utilisateurs avec le rôle "admin"
        return "Action réussie pour l'utilisateur avec le rôle " + requiredRole;
    }
}

