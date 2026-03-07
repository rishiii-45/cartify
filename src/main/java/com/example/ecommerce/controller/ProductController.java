package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", service.getAll());
        return "products";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {

        Product product = service.getProductById(id);

        model.addAttribute("product", product);

        return "add-product"; // reuse same form
    }

    // ⭐ IMAGE UPLOAD LOGIC
    @PostMapping("/save")
    public String saveProduct(
            @ModelAttribute Product product,
            @RequestParam("imageFile") MultipartFile file
    ) throws IOException {

        if (!file.isEmpty()) {
            product.setImageData(file.getBytes());
        }

        service.save(product);

        return "redirect:/admin/products";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {

        Product product = service.getProductById(id);

        if (product != null && product.getImageData() != null) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(product.getImageData());
        }

        return ResponseEntity.notFound().build();
    }

    // DELETE PRODUCT
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/admin/products";
    }
    }
