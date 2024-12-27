package com.example.serviceuser.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.serviceuser.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    // Rechercher un rôle par son nom
}
