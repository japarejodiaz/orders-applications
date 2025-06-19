package com.org.orders.ordersapplications.service;

import com.org.orders.ordersapplications.model.Order;

public interface IOrderServiceComunicator {

    void processOrderAsync(Order order);
    Order getProcessedOrder(String orderId);
    int getTotalProcessedOrders();

}
