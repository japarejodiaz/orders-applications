package com.org.orders.ordersapplications.service.impl;

import com.org.orders.ordersapplications.model.Order;
import com.org.orders.ordersapplications.model.OrderItem;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    void processOrderAsync() {

        // Dado
        Order order = new Order();
        order.setOrderId("ORD-TEST");
        order.setCustomerId("CUST-TEST");
        order.setOrderAmount(new BigDecimal("123.45"));
        order.setOrderItems(List.of(
                new OrderItem("PROD-001", 2, new BigDecimal("50.00"))
        ));

        // Cuando
        orderService.processOrderAsync(order);

        // Entonces (esperamos que se procese en tiempo razonable)
        Awaitility.await()
                .atMost(3, TimeUnit.SECONDS)
                .until(() -> orderService.getProcessedOrder("ORD-TEST") != null);

        // Afirmar que se procesó correctamente
        Order processed = orderService.getProcessedOrder("ORD-TEST");
        assertNotNull(processed);
        assertEquals("CUST-TEST", processed.getCustomerId());
    }


    @Test
    void getProcessedOrder_shouldReturnNullIfNotProcessed() {
        Order result = orderService.getProcessedOrder("NOT_EXIST");
        assertNull(result);
    }

    @Test
    void getTotalProcessedOrders_shouldReturnCorrectCount() {
        assertEquals(0, orderService.getTotalProcessedOrders());

        Order order1 = new Order();
        order1.setOrderId("ORD-2");
        orderService.processOrderAsync(order1);

        Order order2 = new Order();
        order2.setOrderId("ORD-3");
        orderService.processOrderAsync(order2);

        Awaitility.await()
                .atMost(2, TimeUnit.SECONDS)
                .until(() -> orderService.getTotalProcessedOrders() == 2);

        assertEquals(2, orderService.getTotalProcessedOrders());
    }

}