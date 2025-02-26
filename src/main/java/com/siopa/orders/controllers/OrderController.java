package com.siopa.orders.controllers;

import com.siopa.orders.dto.OrderRequest;
import com.siopa.orders.models.EStatus;
import com.siopa.orders.models.Order;
import com.siopa.orders.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing orders.
 * Provides endpoints for retrieving, creating, updating, and deleting orders.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Retrieves all orders.
     *
     * @return a list of all orders
     */
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    /**
     * Retrieves a specific order by its ID.
     *
     * @param orderId the ID of the order
     * @return the order with the specified ID
     */
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    /**
     * Retrieves all orders with a specific status.
     *
     * @param status the status of the orders to retrieve
     * @return a list of orders with the specified status
     */
    @GetMapping("/status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable EStatus status) {
        return orderService.getOrdersByStatus(status);
    }

    /**
     * Creates a new order.
     *
     * @param orderRequest the order details from the request body
     * @return the created order
     */
    @PostMapping
    public Order createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    /**
     * Updates the status of an existing order.
     *
     * @param orderId the ID of the order to update
     * @param status the new status to set
     * @return the updated order
     */
    @PatchMapping("/{orderId}/status")
    public Order updateOrderStatus(@PathVariable String orderId, @RequestParam EStatus status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    /**
     * Deletes an order by its ID.
     *
     * @param orderId the ID of the order to delete
     */
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
    }
}
