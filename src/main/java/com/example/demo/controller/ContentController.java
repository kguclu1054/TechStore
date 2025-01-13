package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContentController {

    @GetMapping("/loginPage")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Giriş başarısız! Lütfen bilgilerinizi kontrol edin.");
        }
        return "loginPage";
    }

    @GetMapping("/req/signup")
    public String signup() {
        return "signup";
    }
}






