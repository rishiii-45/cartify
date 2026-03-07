package com.example.ecommerce.controller;

import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.OrderEntity;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrderController {

    private final CartService cartService;
    private final OrderService orderService;

    public OrderController(CartService cartService,
                           OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @PostMapping("/order/place")
    public String placeOrder() {

        List<CartItem> items = cartService.getCartItems();

        for (CartItem item : items) {

            OrderEntity order = new OrderEntity();
            order.setProductName(item.getProductName());
            order.setPrice(item.getPrice());
            order.setQuantity(item.getQuantity());

            orderService.save(order);
        }

        // clear cart after order
        cartService.clearCart();

        return "order-success";   // ⭐ IMPORTANT
    }
}