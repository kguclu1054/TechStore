package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.KullaniciSiparisleri;
import com.example.demo.entity.Urun;

public class OdemeBilgileri {
    private KullaniciSiparisleri kullaniciSiparisleri;
    private List<Urun> urunler;

    // Getter ve Setter metodları
    public KullaniciSiparisleri getKullaniciSiparisleri() {
        return kullaniciSiparisleri;
    }

    public void setKullaniciSiparisleri(KullaniciSiparisleri kullaniciSiparisleri) {
        this.kullaniciSiparisleri = kullaniciSiparisleri;
    }

    public List<Urun> getUrunler() {
        return urunler;
    }

    public void setUrunler(List<Urun> urunler) {
        this.urunler = urunler;
    }
}

