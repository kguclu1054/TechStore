package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.EmailService;

import org.springframework.ui.Model;

@Controller
public class ForgetPasswordController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/forget_password")
    public String showForgetPasswordForm() {
        return "forgetPassword"; // form sayfası
    }

    // Şifre sıfırlama isteği
    @PostMapping("/forget_password")
    public String handleForgetPassword(@RequestParam String email, Model model) {
        // Burada e-posta kontrolü ve şifre sıfırlama işlemleri yapılır

        // E-posta adresine mesaj gönder
        emailService.sendEmail(
            email, 
            "TechStore Şifre Sıfırlama Talebi", 
            "Şifrenizi sıfırlamak için bu linke tıklayın: [Link]");  // Mail içeriği

        model.addAttribute("message", "Şifre sıfırlama talebi gönderildi.");
        return "/loginPage"; // Veya başka bir yönlendirme sayfası
    }
}

