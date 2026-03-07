package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    // GET ALL PRODUCTS
    public List<Product> getAll() {
        return repo.findAll();
    }

    // SAVE PRODUCT
    public void save(Product product) {
        repo.save(product);
    }

    // GET PRODUCT BY ID
    public Product getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    // DELETE PRODUCT
    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Product getProductById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }


}