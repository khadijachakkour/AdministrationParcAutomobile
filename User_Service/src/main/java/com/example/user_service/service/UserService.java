package com.example.user_service.service;

import com.example.user_service.dto.UserDTO;
import com.example.user_service.entity.User;
import com.example.user_service.entity.Role;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.repository.RoleRepository; // Ajouter un repository pour les rôles
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository; // Repository pour interroger les rôles

    @Autowired
    private PasswordEncoder passwordEncoder; // Injectez le PasswordEncoder

    // Créer un utilisateur
    public UserDTO createUser(UserDTO userDTO) {
        // Créer un objet User
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        // Hachage du mot de passe
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Associer les rôles
        Set<Role> roles = userDTO.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        if (roles.isEmpty()) {
            throw new IllegalArgumentException("User must have at least one valid role.");
        }

        user.setRoles(roles);

        // Enregistrer l'utilisateur
        User savedUser = userRepository.save(user);

        // Retourner l'UserDTO avec les informations
        return new UserDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getPassword(),
                savedUser.getEmail(),
                savedUser.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
        );
    }


    // Mapper un utilisateur existant vers un DTO
    public UserDTO mapToDTO(User user) {
        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), roles);
    }

    // Exemple d'usage : récupérer un utilisateur par son ID
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user); // Mapper l'entité vers le DTO
    }

    // Récupérer tous les utilisateurs
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToDTO)  // Utilise la méthode mapToDTO
                .collect(Collectors.toList());
    }
}
