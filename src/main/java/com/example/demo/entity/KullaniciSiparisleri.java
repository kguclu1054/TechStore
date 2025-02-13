package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class KullaniciSiparisleri {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String adSoyad;
    private String userId;
    private String adres;
    private String sehir;
    private String ilce;
    private String postaKodu;
    private String kartNumarasi;
    private String sonKullanmaTarihi;
    private String cvv;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAdSoyad() {
		return adSoyad;
	}
	public void setAdSoyad(String adSoyad) {
		this.adSoyad = adSoyad;
	}
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
	public String getSehir() {
		return sehir;
	}
	public void setSehir(String sehir) {
		this.sehir = sehir;
	}
	public String getIlce() {
		return ilce;
	}
	public void setIlce(String ilce) {
		this.ilce = ilce;
	}
	public String getPostaKodu() {
		return postaKodu;
	}
	public void setPostaKodu(String postaKodu) {
		this.postaKodu = postaKodu;
	}
	public String getKartNumarasi() {
		return kartNumarasi;
	}
	public void setKartNumarasi(String kartNumarasi) {
		this.kartNumarasi = kartNumarasi;
	}
	public String getSonKullanmaTarihi() {
		return sonKullanmaTarihi;
	}
	public void setSonKullanmaTarihi(String sonKullanmaTarihi) {
		this.sonKullanmaTarihi = sonKullanmaTarihi;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

    // Getter ve Setter metodları
    


}


   
