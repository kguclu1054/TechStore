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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    
    @Autowired
    private MyAppUserRepository myAppUserRepository;

    // Giriş sayfası ve hata mesajı gösterimi
    @GetMapping("/loginPage")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Giriş başarısız! Lütfen bilgilerinizi kontrol edin.");
        }
        return "loginPage"; // loginPage.html sayfasına yönlendiriyor
    }


    // Kayıt olma sayfası
    @GetMapping("/req/signup")
    public String signup() {
        return "signup"; // signup.html sayfasına yönlendiriyor
    }

    // Başarılı giriş sonrası kullanıcı ID'yi döndürme
    @PostMapping("/auth/loginSuccess")
    @ResponseBody
    public ResponseEntity<Object> getUserIdAfterLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Kullanıcı oturum açmamış.");
        }

        String username = authentication.getName();
        Optional<MyAppUser> user = myAppUserRepository.findByUsername(username);

        return user.map(value -> ResponseEntity.ok((Object) value.getId()))
                   .orElseGet(() -> ResponseEntity.status(404).body("Kullanıcı bulunamadı."));
    }


    // Çıkış işlemi
    @GetMapping("/perform_logout")
    public String logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext(); // Kullanıcı oturumunu temizle
        request.getSession().invalidate(); // Oturumu sıfırla
        return "redirect:/loginPage?logout=true"; // Çıkış yaptıktan sonra giriş sayfasına yönlendir
    }
    
    @GetMapping("/userInfo")
    @ResponseBody
    public ResponseEntity<?> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Giriş yapınız.");
        }

        String username = authentication.getName();
        Optional<MyAppUser> user = myAppUserRepository.findByUsername(username);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getEmail());
        } else {
            return ResponseEntity.status(404).body("Kullanıcı bulunamadı.");
        }
    }
}










