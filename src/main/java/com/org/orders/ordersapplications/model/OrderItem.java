package com.org.orders.ordersapplications.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private String itemId;
    private int quantity;
    private double price;

    public OrderItem(String itemId, int quantity, BigDecimal bigDecimal) {
    }
}