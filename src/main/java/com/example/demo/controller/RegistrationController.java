package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.MyAppUser;
import com.example.demo.repository.MyAppUserRepository;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/req/signup")
    public ResponseEntity<String> createUser(@RequestBody MyAppUser user) {
        // Kullanıcı adındaki boşlukları temizliyoruz
        String trimmedUsername = user.getUsername().trim();
        user.setUsername(trimmedUsername);

        logger.debug("Trimmed username: {}", trimmedUsername);

        // Kullanıcı adında boşluk kontrolü
        if (trimmedUsername.contains(" ")) {
            logger.debug("Username contains spaces");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username should not contain spaces.");
        }

        // Kullanıcı adının benzersiz olup olmadığını kontrol ediyoruz
        Optional<MyAppUser> existingUser = myAppUserRepository.findByUsername(trimmedUsername);
        if (existingUser.isPresent()) {
            logger.debug("Username already taken: {}", trimmedUsername);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Kullanıcı adı zaten alınmış.");
        }

        // Şifreyi hash'liyoruz
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        logger.debug("Saving user: {}", user);

        // Veritabanına kaydediyoruz
        myAppUserRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Kullanıcı oluşturuldu");
    }
}




