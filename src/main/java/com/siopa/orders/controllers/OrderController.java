package com.siopa.orders.controllers;

import com.siopa.orders.dto.OrderRequest;
import com.siopa.orders.models.EStatus;
import com.siopa.orders.models.Order;
import com.siopa.orders.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable UUID orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/customer/{customerID}")
    public List<Order> getOrdersByCustomerID(@PathVariable String customerID) {
        return orderService.getOrdersByCustomerID(customerID);
    }

    @GetMapping("/status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable EStatus status) {
        return orderService.getOrdersByStatus(status);
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @PatchMapping("/{orderId}/status")
    public Order updateOrderStatus(@PathVariable UUID orderId, @RequestParam EStatus status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
    }
}
