package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.EmailService;

@Controller
@RequestMapping("/api/contact")
public class ContactMessageController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendContactMail(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam String message) {
        String subject = "Tech Store İletişim Mesajı";
        String text = "Ad: " + name + "\nSoyad: " + surname + "\nE-posta: " + email + "\nMesaj: " + message;

        emailService.sendEmail("kguclu1054@gmail.com", subject, text);
        return "contactMessageSuccessful";
    }
}

