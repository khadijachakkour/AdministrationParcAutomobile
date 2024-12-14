package com.example.security_service.service;


import com.example.user_service.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "${user-service.url}")
public interface UserService {

    @GetMapping("/users/validate")
    boolean isValidUser(@RequestParam String email, @RequestParam String password);

    @GetMapping("/users/role")
    boolean hasRole(@RequestParam String username, @RequestParam String role);

    @GetMapping("/users")
    User getUserByEmail(@RequestParam String email);
}

