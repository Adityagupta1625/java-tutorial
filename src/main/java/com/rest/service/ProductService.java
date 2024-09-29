package com.rest.service;

import java.util.List;
import java.util.Optional;

import com.rest.entity.Product;
import com.rest.repository.ProductRepository;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        try{
            return productRepository.save(product);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to save product: " + e.getMessage());
        }
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> fetchProductById(String id) {
        return productRepository.findById(id);
    }

    public Optional<Product> updateProduct(String id, Product updatedProduct) {
        try {
            Optional<Product> existingProductOptional = productRepository.findById(id);
            if (existingProductOptional.isPresent()) {
                Product existingProduct = existingProductOptional.get();
                existingProduct.setName(updatedProduct.getName());
                existingProduct.setPrice(updatedProduct.getPrice());
                existingProduct.setQuantity(updatedProduct.getQuantity());
                Product savedEntity = productRepository.save(existingProduct);
                return Optional.of(savedEntity);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product: " + e.getMessage());
        }
    }

    public boolean deleteProduct(String id) {
        try {
            productRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete product: " + e.getMessage());
        }
    }

}   
