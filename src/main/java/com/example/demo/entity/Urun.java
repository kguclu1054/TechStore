package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Urun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String urunAdi;
    private Double fiyat;
    private String userId;
    
    @ManyToOne
    @JoinColumn(name = "kullanici_siparis_id")
    private KullaniciSiparisleri kullaniciSiparisleri;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrunAdi() {
		return urunAdi;
	}

	public void setUrunAdi(String urunAdi) {
		this.urunAdi = urunAdi;
	}

	public Double getFiyat() {
		return fiyat;
	}

	public void setFiyat(Double fiyat) {
		this.fiyat = fiyat;
	}

	public KullaniciSiparisleri getKullaniciSiparisleri() {
		return kullaniciSiparisleri;
	}

	public void setKullaniciSiparisleri(KullaniciSiparisleri kullaniciSiparisleri) {
		this.kullaniciSiparisleri = kullaniciSiparisleri;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

    
    
    
}