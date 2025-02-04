package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.MyAppUser;
import com.example.demo.repository.MyAppUserRepository;

import java.util.Optional;

@RestController
public class AuthController {

    private final MyAppUserRepository userRepository;

    public AuthController(MyAppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/api/user")
    public MyAppUser getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            Optional<MyAppUser> user = userRepository.findByUsername(userDetails.getUsername());
            return user.orElse(null);
        }
        return null;
    }
}

