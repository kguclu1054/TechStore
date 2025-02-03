package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    @GetMapping("/adminDashboard")
    public String adminDashboard() {
        return "adminDashboard";  // adminDashboard.html sayfasına yönlendirme
    }

    @GetMapping("/admin/login")
    public String adminLogin() {
        return "adminLogin";  // adminLogin.html sayfasına yönlendirme
    }

    @PostMapping("/perform_admin_login")
    public String performAdminLogin(@RequestParam String username, @RequestParam String password, Model model) {
        // Kullanıcı doğrulama işlemleri
        boolean isAuthenticated = authenticateUser(username, password);
        if (isAuthenticated) {
            return "redirect:/adminDashboard";
        } else {
            model.addAttribute("errorMessage", "Geçersiz kullanıcı adı veya şifre");
            return "adminLogin";
        }
    }

    private boolean authenticateUser(String username, String password) {
        // Gerçek kullanıcı doğrulama mantığını burada ekleyin
        return "admin".equals(username) && "password".equals(password);
    }

    @PostMapping("/products/admin/add")
    public String addProduct(@RequestParam String name, @RequestParam double price, @RequestParam String description,
                             @RequestParam String category, @RequestParam String imageUrl, @RequestParam(required = false) boolean isSlider,
                             Model model) {
        // Ürün ekleme işlemleri
        // Başarı mesajını modele ekle
        model.addAttribute("successMessage", "Ürün başarıyla eklendi");
        return "adminDashboard";
    }
}







