package com.example.ecommerce.controller;

import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProductService productService;
    private final CartService cartService;

    public HomeController(ProductService productService,
                          CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }



    @GetMapping("/")
    public String home(Model model){

        model.addAttribute("products", productService.getAll());
        model.addAttribute("cartCount", cartService.getCartItems().size());

        return "home";
    }



}