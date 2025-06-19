package com.org.orders.ordersapplications.controller;

import com.org.orders.ordersapplications.model.Order;
import com.org.orders.ordersapplications.service.impl.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order Processing API", description = "API para procesar pedidos concurrentes")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Procesar un pedido")
    @PostMapping("/processOrder")
    public ResponseEntity<String> processOrder(@RequestBody Order order) {
        orderService.processOrderAsync(order);
        return ResponseEntity.accepted().body("Pedido en procesamiento");
    }

    @Operation(summary = "Obtener un pedido procesado por ID")
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
        Order order = orderService.getProcessedOrder(orderId);
        if (order == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "Total de pedidos procesados")
    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalProcessedOrders() {
        return ResponseEntity.ok(orderService.getTotalProcessedOrders());
    }
}