package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    public String search(@RequestParam(name = "query") String query, Model model) {
        List<Product> products = productService.searchProducts(query);
        model.addAttribute("products", products);

        // Toplam fiyatı hesaplayın ve loglayın
        double totalPrice = products.stream().mapToDouble(Product::getPrice).sum();
        System.out.println("Toplam Fiyat: " + totalPrice); // Loglama
        model.addAttribute("totalPrice", totalPrice);

        return "search-results"; // Bu, `search-results.html` şablonuna yönlendirecek
    }
}







