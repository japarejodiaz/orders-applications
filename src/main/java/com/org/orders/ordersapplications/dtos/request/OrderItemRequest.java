package com.org.orders.ordersapplications.dtos.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemRequest {
    private String productId;
    private int quantity;
    private BigDecimal price;
}
