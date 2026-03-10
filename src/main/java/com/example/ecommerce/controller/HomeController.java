package com.example.ecommerce.controller;

import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.ProductService;
import jakarta.servlet.http.HttpSession;
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



    @GetMapping("/home")
    public String home(HttpSession session, Model model){

        model.addAttribute("products", productService.getAll());
        model.addAttribute("cartCount", cartService.getCartItems().size());

        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);

        model.addAttribute("activePage","home");

        return "home";
    }

    @GetMapping("/products")
    public String products(Model model){

        model.addAttribute("products", productService.getAll());
        model.addAttribute("cartCount", cartService.getCartItems().size());

        model.addAttribute("activePage","products");

        return "our-products";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("cartCount", cartService.getCartItems().size());
        model.addAttribute("activePage","about");
        return "about";
    }




}