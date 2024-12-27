package com.example.serviceuser.service;


import com.example.serviceuser.dto.UserDTO;
import com.example.serviceuser.dto.RoleDTO;
import com.example.serviceuser.entity.User;
import com.example.serviceuser.entity.Role;
import com.example.serviceuser.repository.RoleRepository;
import com.example.serviceuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // Créer un utilisateur
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); // Enregistrer le mot de passe directement

        // Trouver un rôle par son nom et l'affecter à l'utilisateur
        Role role = roleRepository.findByName(userDTO.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found: " + userDTO.getRoleName()));

        user.setRole(role);  // Affecter un rôle à l'utilisateur

        User savedUser = userRepository.save(user);

        return new UserDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getPassword(),
                savedUser.getEmail(),
                savedUser.getRole().getName()// Accéder au nom du rôle avec getName()
        );
    }

    // Méthode pour convertir un User en UserDTO
    public UserDTO mapToDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getRole().getName());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public boolean validateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }

    public boolean checkUserRole(String email, String roleName) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        return user.getRole().getName().equalsIgnoreCase(roleName); // Vérification du rôle avec getName()
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return mapToDTO(user);
        }
        return null; // Retourner null si l'utilisateur n'existe pas
    }


    //Test pour le Front
    public List<String> getUserRoles(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getRole() != null) {
            return List.of(user.getRole().getName()); // Convertir le rôle unique en liste
        }
        return Collections.emptyList();
    }



}