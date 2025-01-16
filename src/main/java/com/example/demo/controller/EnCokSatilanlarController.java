package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.EnCokSatilanlar;
import com.example.demo.service.EnCokSatilanlarService;


@RestController
@RequestMapping("/api/encoksatilanlar")
public class EnCokSatilanlarController {

    @Autowired
    private EnCokSatilanlarService enCokSatilanlarService;

    @PostMapping
    public ResponseEntity<?> addProducts(@RequestBody List<EnCokSatilanlar> enCokSatilanlarList) {
        List<String> messages = new ArrayList<>();
        for (EnCokSatilanlar product : enCokSatilanlarList) {
            boolean isAdded = enCokSatilanlarService.addProduct(product);
            if (!isAdded) {
                messages.add("Ürün zaten mevcut: " + product.getName());
            }
        }
        if (messages.isEmpty()) {
            messages.add("Ürünler başarıyla eklendi.");
        }
        return ResponseEntity.ok(messages);
    }
}





