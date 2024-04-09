package com.example.etrade.controller;

import com.example.etrade.model.Cart;
import com.example.etrade.model.Customer;
import com.example.etrade.model.Order;
import com.example.etrade.service.CustomerService;
import com.example.etrade.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestBody Cart cart) {
        Order order = orderService.placeOrder(cart);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrderForCode(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getAllOrdersForCustomer(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        List<Order> orders = orderService.getAllOrdersForCustomer(customer);
        return ResponseEntity.ok(orders);
    }
}
