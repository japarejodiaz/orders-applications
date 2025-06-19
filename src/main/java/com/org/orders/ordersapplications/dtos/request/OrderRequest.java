package com.org.orders.ordersapplications.dtos.request;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequest {
    private String orderId;
    private String customerId;
    private BigDecimal orderAmount;
    private List<OrderItemRequest> orderItems;
}
