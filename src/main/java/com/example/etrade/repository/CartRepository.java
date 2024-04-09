package com.example.etrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.etrade.model.Cart;
public interface CartRepository extends JpaRepository<Cart,Long> {

}
