package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

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

    // Profile sayfasına yönlendiren, oturumdaki userId'yi kontrol eden endpoint
    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId"); // Session'dan userId alınıyor
        if (userId != null) {
            // Eğer userId varsa, profile sayfasını döndürüyoruz
            model.addAttribute("userId", userId);
            return "profile"; // profile.html sayfasına yönlendirme
        } else {
            // Eğer userId yoksa, loginPage'e yönlendiriyoruz
            return "redirect:/loginPage";
        }
    }
}







