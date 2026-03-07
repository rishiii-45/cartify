package com.example.ecommerce.service;

import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.OrderEntity;
import com.example.ecommerce.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // ✅ Save single order
    public void save(OrderEntity order) {
        orderRepository.save(order);
    }

    // ✅ Place order from cart items
    public void placeOrder(List<CartItem> cartItems) {

        double total = 0;

        for (CartItem item : cartItems) {

            OrderEntity order = new OrderEntity();

            order.setProductName(item.getProductName());
            order.setPrice(item.getPrice());
            order.setQuantity(item.getQuantity());

            total += item.getPrice() * item.getQuantity();

            orderRepository.save(order);
        }
    }
}