package com.example.ecommerce.service;

import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // SAVE ITEM TO CART
    public void save(CartItem item) {
        cartRepository.save(item);
    }

    // ✅ ADD THIS METHOD (THIS IS YOUR ERROR FIX)
    public List<CartItem> getCartItems() {
        return cartRepository.findAll();
    }

    // CLEAR CART
    public void clearCart() {
        cartRepository.deleteAll();
    }

    public void removeFromCart(Long id){
        cartRepository.deleteById(id);
    }

    public void updateQuantity(Long id, int change){
        CartItem item = cartRepository.findById(id).orElse(null);
        if(item != null){
            int newQty = item.getQuantity() + change;
            if(newQty > 0){
                item.setQuantity(newQty);
                cartRepository.save(item);
            }
        }
    }
}