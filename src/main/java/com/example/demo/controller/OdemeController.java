package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.KullaniciSiparisleri;
import com.example.demo.entity.Urun;
import com.example.demo.service.KullaniciSiparisleriService;
import com.example.demo.service.OdemeBilgileri;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/odeme")
public class OdemeController {
    @Autowired
    private KullaniciSiparisleriService kullaniciSiparisleriService;

    @PostMapping("/kaydet")
    public String kaydetKullaniciVeUrunler(@RequestBody OdemeBilgileri odemeBilgileri) {
        KullaniciSiparisleri kullaniciSiparisleri = odemeBilgileri.getKullaniciSiparisleri();
        List<Urun> urunler = odemeBilgileri.getUrunler();
        KullaniciSiparisleri kaydedilenKullaniciSiparisleri = kullaniciSiparisleriService.kaydetKullaniciVeUrunler(kullaniciSiparisleri, urunler);
        
        // Ödeme başarılı olduğunda yönlendirme
        return "redirect:/odeme/basarili";
    }

    @GetMapping("/basarili")
    public String odemeBasari() {
        return "odemeBasarili"; // src/main/resources/templates/odemeBasarili.html dosyasını döndürür
    }
}




