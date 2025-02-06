package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MyAppUser;
import com.example.demo.repository.MyAppUserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MyAppUserService implements UserDetailsService {

    private final MyAppUserRepository myAppUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MyAppUserService(@Lazy PasswordEncoder passwordEncoder, MyAppUserRepository myAppUserRepository) {
        this.myAppUserRepository = myAppUserRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
   

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyAppUser user = myAppUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        log.info("User found: {}", username);

        // Kullanıcının rolünü ve diğer bilgileri veritabanından alabilirsiniz.
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Şifre veritabanında hash'li olmalı
                .roles("USER") // Gereksinime göre, kullanıcı rollerini dinamik hale getirebilirsiniz
                .build();
    }

    // Kullanıcıya ait şifreyi doğrulamak için helper method (isteğe bağlı)
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword); // Raw şifreyi encoded şifreyle karşılaştırır
    }
}
