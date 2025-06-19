package com.org.orders.ordersapplications.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.orders.ordersapplications.model.Order;
import com.org.orders.ordersapplications.service.impl.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void processOrder_returnsAccepted() throws Exception {
        Order order = new Order();
        order.setOrderId("ORD-1");
        order.setCustomerId("CUST-1");
        order.setOrderAmount(new BigDecimal("100.00"));

        var result = mockMvc.perform(post("/orders/processOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Pedido en procesamiento"));

        System.out.println("processOrder_returnsAccepted response: " + result.andReturn().getResponse().getContentAsString());
        Mockito.verify(orderService).processOrderAsync(any(Order.class));
    }

    @Test
    void getOrder_returnsOrderIfExists() throws Exception {
        Order order = new Order();
        order.setOrderId("ORD-2");
        order.setCustomerId("CUST-2");
        order.setOrderAmount(new BigDecimal("200.00"));

        Mockito.when(orderService.getProcessedOrder(eq("ORD-2"))).thenReturn(order);

        var result = mockMvc.perform(get("/orders/ORD-2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("ORD-2"))
                .andExpect(jsonPath("$.customerId").value("CUST-2"));

        System.out.println("getOrder_returnsOrderIfExists response: " + result.andReturn().getResponse().getContentAsString());

    }

    @Test
    void getOrder_returnsNotFoundIfNotExists() throws Exception {
        Mockito.when(orderService.getProcessedOrder(eq("NOT_FOUND"))).thenReturn(null);

        var result = mockMvc.perform(get("/orders/NOT_FOUND"))
                .andExpect(status().isNotFound());
        System.out.println("getOrder_returnsNotFoundIfNotExists status: " + result.andReturn().getResponse().getStatus());
    }

    @Test
    void getTotalProcessedOrders_returnsCount() throws Exception {
        Mockito.when(orderService.getTotalProcessedOrders()).thenReturn(5);

        var result = mockMvc.perform(get("/orders/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));

        System.out.println("getTotalProcessedOrders_returnsCount response: " + result.andReturn().getResponse().getContentAsString());
    }
}