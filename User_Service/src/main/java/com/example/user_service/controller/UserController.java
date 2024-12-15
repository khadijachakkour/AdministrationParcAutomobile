package com.example.user_service.controller;

import com.example.user_service.dto.UserDTO;
import com.example.user_service.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "User Management System", tags = {"Users"})
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Create a new user", response = UserDTO.class)
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@ApiParam(value = "User to be created", required = true) @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a user by ID", response = UserDTO.class)
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@ApiParam(value = "ID of the user to retrieve", required = true) @PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Get all users", response = List.class)
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiOperation(value = "Validate user credentials", response = Boolean.class)
    @GetMapping("/validate")
    public ResponseEntity<Boolean> isValidUser(@RequestParam String email, @RequestParam String password) {
        boolean isValid = userService.validateUser(email, password);
        if (!isValid) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @ApiOperation(value = "Check user role", response = Boolean.class)
    @GetMapping("/hasRole")
    public ResponseEntity<Boolean> hasRole(@RequestParam String email, @RequestParam String role) {
        boolean hasRole = userService.checkUserRole(email, role);
        if (!hasRole) {
            return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


    // Endpoint pour récupérer un utilisateur par email
    /*@GetMapping("/email/{email}")
    public UserDTO getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }*/

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getByEmail(@PathVariable String email) {
        // Recherche de l'utilisateur par email via le service
        UserDTO userDTO = userService.getUserByEmail(email);

        // Si l'utilisateur est trouvé
        if (userDTO != null) {
            // Retourner une réponse HTTP 200 OK avec l'objet UserDTO dans le corps de la réponse
            return ResponseEntity.ok(userDTO);
        }

        // Si l'utilisateur n'est pas trouvé, retourner une réponse HTTP 404 Not Found
        return ResponseEntity.notFound().build();
    }




}
