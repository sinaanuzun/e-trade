package com.example.etrade.repository;

import com.example.etrade.model.Customer;
import com.example.etrade.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByCustomer(Customer customer);

}
