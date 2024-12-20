package com.example.serviceuser.controller;


import com.example.serviceuser.dto.UserDTO;
import com.example.serviceuser.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Users", description = "User Management System")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Create a new user", description = "Creates a new user and returns the created user object.")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(
            @Parameter(description = "User to be created", required = true) @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a user by ID", description = "Retrieves a user by their ID.")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(description = "ID of the user to retrieve", required = true) @PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users.")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Validate user credentials", description = "Validates user credentials based on email and password.")
    @GetMapping("/validate")
    public ResponseEntity<Boolean> isValidUser(
            @RequestParam String email, @RequestParam String password) {
        boolean isValid = userService.validateUser(email, password);
        if (!isValid) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @Operation(summary = "Check user role", description = "Checks if the user has a specific role.")
    @GetMapping("/hasRole")
    public ResponseEntity<Boolean> hasRole(
            @RequestParam String email, @RequestParam String role) {
        boolean hasRole = userService.checkUserRole(email, role);
        if (!hasRole) {
            return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /* @Operation(summary = "Get a user by email", description = "Retrieves a user by their email.")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getByEmail(
            @Parameter(description = "Email of the user to retrieve", required = true) @PathVariable String email) {
        UserDTO userDTO = userService.getUserByEmail(email);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }

     */

    @Operation(summary = "Get a user by email", description = "Retrieves a user by their email.")
    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String, String>> getByEmail(
            @Parameter(description = "Email of the user to retrieve", required = true) @PathVariable String email) {
        UserDTO userDTO = userService.getUserByEmail(email);
        if (userDTO != null) {
            // Construire une carte pour les informations de l'utilisateur
            Map<String, String> infos_user = new HashMap<>();
            infos_user.put("email", userDTO.getEmail());
            infos_user.put("password", userDTO.getPassword()); // Vérifiez si c'est sensible
            infos_user.put("scope", userDTO.getRoleName()); // Assumez que `getRole` retourne un rôle sous forme de chaîne

            return ResponseEntity.ok(infos_user);
        }
        return ResponseEntity.notFound().build();
    }

}