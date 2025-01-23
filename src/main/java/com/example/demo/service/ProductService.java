package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.repository.ProductRepository;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    // Bu metot, id ile ürünü ve onun resimlerini döndürecek
    public Product findProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        // Eğer ürün mevcutsa, resimleri ekle ve döndür
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            // ProductImages ile ilişkili verileri al
            product.setProductImages(productImageRepository.findByProductId(id));
            return product;
        }

        // Ürün bulunamazsa null döndür
        return null;
    }

    // Yeni arama metodu
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }
}









