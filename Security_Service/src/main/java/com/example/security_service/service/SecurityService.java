package com.example.security_service.service;

import com.example.security_service.dto.LoginRequest;
import com.example.security_service.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final JwtUtil jwtUtil;
    private final UserService userService;  // Feign Client

    @Autowired
    public SecurityService(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    public String authenticateUser(LoginRequest loginRequest) {
        if (!userService.isValidUser(loginRequest.getEmail(), loginRequest.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        // Si les informations sont valides, générer le JWT
        return jwtUtil.generateToken(loginRequest.getEmail());
    }

    public void checkUserRole(String token, String requiredRole) {
        // Extraire le nom d'utilisateur à partir du token
        String username = jwtUtil.getUsernameFromToken(token);

        if (username == null) {
            throw new UnauthorizedException("Token is invalid");
        }

        // Appeler le service User pour récupérer les rôles
        if (!userService.hasRole(username, requiredRole)) {
            throw new UnauthorizedException("User does not have the required role");
        }
    }
}

