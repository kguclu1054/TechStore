package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add-to-cart/{id}")
    public String addToCheckout(@PathVariable("id") Long id, Model model) {
        System.out.println("Request received for product id: " + id);
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            model.addAttribute("product", product);
            System.out.println("Product found: " + product.getName());
            return "redirect:/checkout";  // Başarılıysa yönlendirme
        } else {
            model.addAttribute("error", "Ürün bulunamadı!");
            System.out.println("Product not found with id: " + id);
            return "error";  // Hata sayfası
        }
    }

    @GetMapping
    public String getCheckoutPage(Model model) {
        return "checkout";
    }

    @GetMapping("/items")
    @ResponseBody
    public List<Product> getCheckoutItems() {
        return productRepository.findAll();
    }
}










