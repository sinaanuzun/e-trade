package com.example.etrade.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Orders")
public class Order extends BaseEntity{

    private Long orderTrackingNumber;

    private String deliveryAddress;

    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_customer_id")
    private Customer customer;
}
