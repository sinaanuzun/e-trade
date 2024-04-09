package com.example.etrade.service;

import com.example.etrade.model.Cart;
import com.example.etrade.model.CartItem;
import com.example.etrade.model.Customer;
import com.example.etrade.model.Order;
import com.example.etrade.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Transactional
    public Order placeOrder(Cart cart) {
        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setTotalPrice(cart.getTotalAmount());

        order = orderRepository.save(order);

        for (CartItem cartItem : cart.getCartItems()) {
            cartItem.setOriginalPrice(cartItem.getProduct().getPrice());
        }

        calculateTotalPrice(cart);
        cart.getCartItems().clear();
        cart.setTotalAmount(0.0);
        return order;
    }

    public Order getOrderForCode(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("ID'ye sahip sipariş bulunamadı: " + orderId));
    }

    public List<Order> getAllOrdersForCustomer(Customer customer) {
        return orderRepository.findByCustomer(customer);
    }

    private void calculateTotalPrice(Cart cart) {
        double toplam = 0.0;
        for (CartItem item : cart.getCartItems()) {
            toplam += item.getPrice() * item.getQuantity();
        }
        cart.setTotalAmount(toplam);
    }

}
