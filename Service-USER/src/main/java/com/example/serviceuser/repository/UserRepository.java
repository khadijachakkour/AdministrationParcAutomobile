package com.example.serviceuser.repository;

import com.example.serviceuser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
