package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public String getProduct(@PathVariable("id") Long id, Model model) {
        Product product = productService.findProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "product";
        } else {
            return "error";
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public String addProducts(@RequestBody List<Product> products) {
        try {
            productService.saveProducts(products);
            return "Ürünler başarıyla eklendi!";
        } catch (Exception e) {
            return "Hata oluştu: " + e.getMessage();
     
        }
        
    } 
    
    @GetMapping("/filter")
    public String filterProducts(@RequestParam String category, Model model) {
        List<Product> products = productService.findProductsByCategory(category);
        model.addAttribute("products", products);
        return "search-results"; // search-results.html şablonuna yönlendir
    }

}















