package com.example.ecommerce.controller;

import java.util.List;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService,
                          ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    // ADD TO CART
    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        Product product = productService.getProductById(id);

        CartItem item = new CartItem();
        item.setProductName(product.getName());
        item.setPrice(product.getPrice());
        item.setQuantity(1);

        cartService.save(item);

        redirectAttributes.addFlashAttribute("success", "Added to cart!");

        return "redirect:/products";
    }

    // VIEW CART
    @GetMapping
    public String viewCart(Model model) {

        List<CartItem> items = cartService.getCartItems();

        model.addAttribute("cartItems", items);

        // ⭐ ADD THIS
        model.addAttribute("cartCount", items.size());

        double total = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        model.addAttribute("total", total);

        model.addAttribute("activePage","cart");

        return "cart";
    }

    @PostMapping("/remove/{id}")
    public String removeItem(@PathVariable Long id) {
        cartService.removeFromCart(id);
        return "redirect:/cart";
    }

    @PostMapping("/increase/{id}")
    public String increaseQty(@PathVariable Long id){
        cartService.updateQuantity(id, 1);
        return "redirect:/cart";
    }

    @PostMapping("/decrease/{id}")
    public String decreaseQty(@PathVariable Long id){
        cartService.updateQuantity(id, -1);
        return "redirect:/cart";
    }
}