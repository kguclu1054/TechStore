package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.CartItemRepository;
import com.example.demo.entity.CartItem;

@Service
public class CheckoutService {

    private final CartItemRepository cartItemRepository;

    public CheckoutService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    // Kullanıcıya ait tüm sepet öğelerini silmek için
    @Transactional
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId); // Kullanıcıya ait tüm öğeleri sil
    }

    // Tek bir öğeyi silmek için (id'ye göre)
    @Transactional
    public void removeItem(Long id) {
        cartItemRepository.deleteById(id); // Belirli id'ye sahip öğeyi sil
    }
}

