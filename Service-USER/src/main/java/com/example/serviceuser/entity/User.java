package com.example.serviceuser.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Remplacez la relation ManyToMany par ManyToOne pour un seul rôle
    @ManyToOne(fetch = FetchType.LAZY) // Utilisation de LAZY par défaut
    @JoinColumn(name = "role", nullable = false) // Colonne role_id dans la table User
    private Role role;

    public User() {
        // Constructeur par défaut
    }

    // Constructeur paramétré
    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}