package com.example.security_service.dto;


public class LoginRequest {

    private String email;
    private String password; // Vous pouvez ajouter d'autres champs nécessaires

    // Getter pour email
    public String getEmail() {
        return email;
    }

    // Setter pour email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter pour password
    public String getPassword() {
        return password;
    }

    // Setter pour password
    public void setPassword(String password) {
        this.password = password;
    }
}
