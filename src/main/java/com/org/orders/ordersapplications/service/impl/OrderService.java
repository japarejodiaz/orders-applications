package com.org.orders.ordersapplications.service.impl;

import com.org.orders.ordersapplications.model.Order;
import com.org.orders.ordersapplications.service.IOrderServiceComunicator;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OrderService implements IOrderServiceComunicator {

    private final ExecutorService executor = Executors.newFixedThreadPool(100);
    private final ConcurrentMap<String, Order> processedOrders = new ConcurrentHashMap<>();

    @Override
    public void processOrderAsync(Order order) {
        CompletableFuture.runAsync(() -> processOrder(order), executor);
    }


    private void processOrder(Order order) {
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(100, 500));
            processedOrders.put(order.getOrderId(), order);
            System.out.printf("Processed order %s in %d ms%n", order.getOrderId(), System.currentTimeMillis() - start);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Order getProcessedOrder(String orderId) {
        return processedOrders.get(orderId);
    }

    public int getTotalProcessedOrders() {
        return processedOrders.size();
    }
}