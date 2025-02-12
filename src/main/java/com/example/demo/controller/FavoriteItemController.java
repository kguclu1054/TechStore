package com.example.demo.controller;

import com.example.demo.entity.FavoriteItem;
import com.example.demo.repository.FavoriteItemRepository;

import jakarta.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Collections;
import java.util.HashMap;

@Controller
@RequestMapping("/favoriler")  // "/favoriler" URL'si
public class FavoriteItemController {

    private final FavoriteItemRepository favoriteItemRepository;

    public FavoriteItemController(FavoriteItemRepository favoriteItemRepository) {
        this.favoriteItemRepository = favoriteItemRepository;
    }

    // Favorilere ürün ekleme
    @PostMapping("/add")
    public ResponseEntity<?> addFavorite(@RequestBody FavoriteItem favoriteItem) {
        try {
            boolean exists = favoriteItemRepository.existsByProductIdAndUserId(favoriteItem.getProductId(), favoriteItem.getUserId());
            
            if (exists) {
                // Eğer zaten varsa, kullanıcıya bilgi ver
                return ResponseEntity.status(400).body(Collections.singletonMap("error", "Bu ürün zaten favorilerinizde."));
            }

            // Favori item'ını kaydet
            favoriteItemRepository.save(favoriteItem);

            // Favorinin başarılı bir şekilde eklenip eklenmediğini belirt
            return ResponseEntity.ok(Collections.singletonMap("success", true));  // 200 başarılı dönüş
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("error", "Favoriye eklerken bir hata oluştu!"));
        }
    }



    // Kullanıcının favori ürünlerini getirme (HTML sayfasına gönderme)
    @GetMapping("/{userId}")
    public String getFavoritesPage(@PathVariable Long userId, Model model) {
        // userId'ye özel favori ürünleri çekiyoruz
        List<FavoriteItem> favoriteItems = favoriteItemRepository.findByUserId(userId);
        
        if(favoriteItems.isEmpty()) {
            model.addAttribute("message", "Henüz favori ürününüz yok.");
        }

        // Favori ürünleri model'e gönderiyoruz
        model.addAttribute("favoriteItems", favoriteItems);  
        return "favoriler";  // templates/favoriler.html dosyasını döndürüyoruz
    }
    
    @Transactional
    @DeleteMapping("/remove")
    public ResponseEntity<Map<String, String>> removeFavorite(@RequestBody FavoriteItem favoriteItem) {
        Map<String, String> response = new HashMap<>();
        try {
            // Favori ürününü silme işlemi
            favoriteItemRepository.deleteByUserIdAndProductId(favoriteItem.getUserId(), favoriteItem.getProductId());
            response.put("message", "Favori başarıyla silindi.");
            // Başarılı ise JSON döndürüyoruz
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Silme işlemi başarısız: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }



}











