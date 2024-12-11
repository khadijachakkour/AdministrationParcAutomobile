package com.example.user_service.service;

import com.example.user_service.dto.UserDTO;
import com.example.user_service.entity.User;
import com.example.user_service.entity.Role;
import com.example.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Créer un utilisateur
    @Autowired
    private PasswordEncoder passwordEncoder; // Injectez le PasswordEncoder


    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        // Hachage du mot de passe
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Set<Role> roles = userDTO.getRoles().stream()
                .map(roleName -> new Role(roleName))
                .collect(Collectors.toSet());
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
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
        return new UserDTO(user.getId(), user.getUsername(),user.getPassword(), user.getEmail(), roles);
    }

    // Exemple d'usage
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user); // Mapper l'entité vers le DTO
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> mapToDTO(user))  // Utilise la méthode mapToDTO
                .collect(Collectors.toList());
    }
}
