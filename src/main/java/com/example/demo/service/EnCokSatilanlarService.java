package com.example.demo.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.EnCokSatilanlar;
import com.example.demo.repository.EnCokSatilanlarRepository;

import jakarta.transaction.Transactional;

@Service
public class EnCokSatilanlarService {

    private static final Logger logger = LoggerFactory.getLogger(EnCokSatilanlarService.class);

    @Autowired
    private EnCokSatilanlarRepository enCokSatilanlarRepository;

    @Transactional
    public boolean addProduct(EnCokSatilanlar product) {
        Optional<EnCokSatilanlar> existingProduct = enCokSatilanlarRepository.findByName(product.getName());
        if (existingProduct.isPresent()) {
            logger.warn("Product already exists: {}", product.getName());
            return false; // Ürün zaten varsa ekleme
        }
        enCokSatilanlarRepository.save(product);
        logger.info("Product saved successfully: {}", product.getName());
        return true;
    }
}




