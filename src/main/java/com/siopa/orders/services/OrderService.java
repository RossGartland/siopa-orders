package com.siopa.orders.services;

import com.siopa.orders.dto.OrderRequest;
import com.siopa.orders.models.EStatus;
import com.siopa.orders.models.Order;
import com.siopa.orders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getOrdersByCustomerID(String customerID) {
        // Convert the customerID string to UUID.
        UUID custUUID = UUID.fromString(customerID);
        return orderRepository.findByCustomerID(custUUID);
    }

    public List<Order> getOrdersByStatus(EStatus status) {
        return orderRepository.findByStatus(status);
    }

    public Order createOrder(OrderRequest request) {
        Order order = Order.builder()
                .customerID(UUID.fromString(request.getCustomerID()))
                .customerEmail(request.getCustomerEmail())
                .storeID(request.getStoreID())
                .totalItemCost(request.getTotalItemCost())
                .isDelivery(request.isDelivery())
                .isCollection(request.isCollection())
                .deliveryFee(request.getDeliveryFee())
                .deliveryAddress(request.getDeliveryAddress())
                .totalCost(request.getTotalCost())
                .status(request.getStatus())
                .customerLat(request.getCustomerLat())
                .customerLng(request.getCustomerLng())
                .build();
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(UUID orderId, EStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void deleteOrder(UUID orderId) {
        orderRepository.deleteById(orderId);
    }
}