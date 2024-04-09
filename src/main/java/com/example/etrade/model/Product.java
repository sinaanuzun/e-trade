package com.example.etrade.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Product")
public class Product extends BaseEntity{

    private String productName;

    private double price;

    private int stock;
}
