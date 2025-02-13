package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.KullaniciSiparisleri;

@Repository
public interface KullaniciSiparisleriRepository extends JpaRepository<KullaniciSiparisleri, Long> {

	KullaniciSiparisleri save(KullaniciSiparisleri kullaniciSiparisleri);
    List<KullaniciSiparisleri> findByUserId(String userId);
    
}
