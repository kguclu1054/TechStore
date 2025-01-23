package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable("id") Long id, Model model) {
        Product product = productService.findProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "product";  // product.html sayfasına yönlendir
        } else {
            return "error";  // error.html sayfası
        }
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<Product> products = productService.searchProducts(query);
        if (products.size() == 1) {
            return "redirect:/products/" + products.get(0).getId();
        }
        model.addAttribute("products", products);
        return "search-results";  // search-results.html sayfasına yönlendir
    }
}














