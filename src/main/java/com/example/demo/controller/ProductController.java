package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;


@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable("id") Long id, Model model) {
        // findProductById metodunu çağır
        Product product = productService.findProductById(id);
        
        // Eğer ürün bulunursa, modele ekle
        if (product != null) {
            model.addAttribute("product", product);
            return "product";  // product.html sayfasına yönlendir
        } else {
            // Ürün bulunamazsa, hata sayfasına yönlendir
            return "error";  // error.html sayfası
        }
    }
}












