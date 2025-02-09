package com.example.demo.controller;

import com.example.demo.entity.CartItem;
import com.example.demo.repository.CartItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/purchasePage")
public class PurchasePageController {

    private final CartItemRepository cartItemRepository;

    public PurchasePageController(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    // /purchasePage endpoint'i, sepet verilerini model ile frontend'e gönderir
    @GetMapping("")
    public String purchasePage(Model model) {
        Long userId = 1L; // Örnek kullanıcı ID'si

        // Kullanıcıya ait sepet verilerini veritabanından al
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        // Sepet verilerini model ile view'a gönder
        model.addAttribute("cartItems", cartItems);

        return "purchasePage"; // purchasePage.html sayfasına yönlendir
    }

    @GetMapping("/items/{userId}")
    public List<CartItem> getPurchaseItems(@PathVariable Long userId) {
        return cartItemRepository.findByUserId(userId);
    }
}



