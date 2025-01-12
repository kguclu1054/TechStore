package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.MyAppUser;
import com.example.demo.model.MyAppUserRepository;

@RestController
public class RegistrationController {

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/req/signup")
    public MyAppUser createUser(@RequestBody MyAppUser user) {
        // Şifreyi hash'liyoruz
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Veritabanına kaydediyoruz
        return myAppUserRepository.save(user);
    }
}


