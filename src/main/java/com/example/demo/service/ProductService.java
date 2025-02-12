package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    public Product findProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setProductImages(productImageRepository.findByProductId(id));
            return product;
        }
        return null;
    }

    public void saveProducts(List<Product> products) {
        for (Product product : products) {
            if (productRepository.findByNameContainingIgnoreCase(product.getName()).isEmpty()) {
                productRepository.save(product);
            }
        }
    }

    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }

	public List<Product> findProductsByCategory(String category) {
		return productRepository.findByCategoryIgnoreCase(category);
	}
}
