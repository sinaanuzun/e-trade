package com.example.etrade.controller;

import com.example.etrade.model.Cart;
import com.example.etrade.model.Product;
import com.example.etrade.service.CartService;
import com.example.etrade.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addProductToCart(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProduct(productId);
        cartService.addProductToCart(cart, product, quantity);
        return ResponseEntity.ok("Ürün başarıyla sepete eklendi.");
    }


    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long cartId){
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/empty")
    public ResponseEntity<String> emptyCart(@RequestParam Long cartId) {
        cartService.emptyCart(cartId);
        return ResponseEntity.ok("Sepet başarıyla boşaltıldı.");
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeProductFromCart(@RequestParam Long cartId, @RequestParam Long productId) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProduct(productId);
        cartService.removeProductFromCart(cart, product);
        return ResponseEntity.ok("Ürün başarıyla sepetteki çıkarıldı.");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCart(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProduct(productId);
        cartService.updateCart(cart, product, quantity);
        return ResponseEntity.ok("Sepet güncellendi.");
    }

}
