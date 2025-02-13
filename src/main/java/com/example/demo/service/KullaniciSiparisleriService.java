package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.KullaniciSiparisleri;
import com.example.demo.entity.Urun;
import com.example.demo.repository.KullaniciSiparisleriRepository;
import com.example.demo.repository.UrunRepository;

import jakarta.transaction.Transactional;

@Service
public class KullaniciSiparisleriService {
    @Autowired
    private KullaniciSiparisleriRepository kullaniciSiparisleriRepository;
    @Autowired
    private UrunRepository urunRepository;

    @Transactional
    public KullaniciSiparisleri kaydetKullaniciVeUrunler(KullaniciSiparisleri kullaniciSiparisleri, List<Urun> urunler) {
        KullaniciSiparisleri kaydedilenKullaniciSiparisleri = kullaniciSiparisleriRepository.save(kullaniciSiparisleri);
        urunler.forEach(urun -> {
            urun.setKullaniciSiparisleri(kaydedilenKullaniciSiparisleri);
            urun.setUserId(kullaniciSiparisleri.getUserId());
            urunRepository.save(urun);
            System.out.println("Ürün kaydedildi: " + urun.getUrunAdi() + ", " + urun.getFiyat());  // Log ekleyelim
        });
        return kaydedilenKullaniciSiparisleri;
    }
}








