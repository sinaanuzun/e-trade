package com.example.etrade.service;

import com.example.etrade.model.Cart;
import com.example.etrade.model.CartItem;
import com.example.etrade.model.Product;
import com.example.etrade.repository.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Transactional
    public void addProductToCart(Cart cart, Product product, int quantity) {

        if (product.getStock() < quantity) {
            throw new EntityNotFoundException("Yeterli stok bulunmuyor..");
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setPrice(product.getPrice());
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart);
        cart.getCartItems().add(cartItem);

        calculateTotalPrice(cart);
        cartRepository.save(cart);
    }

    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Kimliğe sahip sepet bulunamadı:" + cartId));
    }

    @Transactional
    public void emptyCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Kimliğe sahip sepet bulunamadı: " + cartId));

        cart.getCartItems().clear();
        cart.setTotalAmount(0.0);

        cartRepository.save(cart);
    }

    @Transactional
    public void removeProductFromCart(Cart cart, Product product) {

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            cart.getCartItems().remove(cartItem);
            calculateTotalPrice(cart);
            cartRepository.save(cart);
        }
    }

    @Transactional
    public void updateCart(Cart cart, Product product, int quantity) {
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            calculateTotalPrice(cart);
            cartRepository.save(cart);
        }
    }

    private void calculateTotalPrice(Cart cart) {
        double toplam = 0.0;
        for (CartItem item : cart.getCartItems()) {
            toplam += item.getPrice() * item.getQuantity();
        }
        cart.setTotalAmount(toplam);
    }
}
