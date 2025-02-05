package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.MyAppUser;
import com.example.demo.repository.MyAppUserRepository;

import java.util.Optional;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
    private MyAppUserRepository userRepository;

    // Login sayfası, hata mesajı gösterimi
    @GetMapping("/loginPage")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Giriş başarısız! Lütfen bilgilerinizi kontrol edin.");
        }
        return "loginPage"; // loginPage.html sayfasına yönlendiriyor
    }

    // Signup sayfasına yönlendiriyor
    @GetMapping("/req/signup")
    public String signup() {
        return "signup"; // signup.html sayfasına yönlendiriyor
    }
    
    @PostMapping("/auth/loginSuccess")
    @ResponseBody
    public ResponseEntity<?> getUserIdAfterLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Kullanıcı oturum açmamış.");
        }

        String username = authentication.getName();
        Optional<MyAppUser> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getId());
        } else {
            return ResponseEntity.status(404).body("Kullanıcı bulunamadı.");
        }
    }

}






