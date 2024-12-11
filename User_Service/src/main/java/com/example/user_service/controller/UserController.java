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

import java.util.List;

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
}
