package com.example.demo.repository;

import com.example.demo.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Kullanıcı ID'sine göre sepet öğelerini al
	 List<CartItem> findByUserId(Long userId);

    // Kullanıcı ID'sine göre tüm öğeleri sil
    void deleteByUserId(Long userId);
    
    void deleteById(Long id); 
}



