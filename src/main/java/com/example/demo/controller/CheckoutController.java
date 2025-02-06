package com.example.demo.controller;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.CheckoutService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    
	private CheckoutService checkoutService;
    
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    // Sepete ürün eklemek için endpoint
    @PostMapping("/add-to-cart/{userId}")
    public String addToCheckout(@PathVariable("userId") Long userId, @RequestBody CartItem cartItem, Model model) {
        Product product = productRepository.findById(cartItem.getProductId()).orElse(null);
        if (product != null) {
            cartItem.setDescription(product.getDescription());
            cartItem.setPrice(product.getPrice());
            cartItem.setImageUrl(product.getImageUrl());
            cartItem.setUserId(userId);  // User ID ile ilişkilendirme
            cartItemRepository.save(cartItem);
            model.addAttribute("product", product);
            return "redirect:/checkout";
        } else {
            model.addAttribute("error", "Ürün bulunamadı!");
            return "error";
        }
    }

    // Sepet sayfasını getirme
    @GetMapping
    public String getCheckoutPage(Model model) {
        return "checkout"; // checkout.html sayfasını döndürüyor
    }

    // Kullanıcıya ait sepet öğelerini almak için endpoint
    @GetMapping("/items/{userId}")
    @ResponseBody
    public List<CartItem> getCheckoutItems(@PathVariable("userId") Long userId) {
        return cartItemRepository.findByUserId(userId);  // Kullanıcıya özel sepeti getir
    }

    // Sepetten ürün silme
    @PostMapping("/remove-all/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        checkoutService.clearCart(userId);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/remove-item/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long itemId) {
        checkoutService.removeItem(itemId);
        return ResponseEntity.ok().build();
    }


    
    @GetMapping("/loginPage")
    public String loginPage(@RequestParam(value = "logout", required = false) String logout, Model model) {
        if (logout != null) {
            model.addAttribute("logoutMessage", "Başarıyla çıkış yaptınız!");
        }
        return "loginPage"; // loginPage.html sayfasına yönlendiriyor
    }
}
