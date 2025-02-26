package com.siopa.orders.services;

import com.siopa.orders.dto.OrderRequest;
import com.siopa.orders.models.EStatus;
import com.siopa.orders.models.Order;
import com.siopa.orders.models.OrderItem;
import com.siopa.orders.repositories.OrderItemRepository;
import com.siopa.orders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing orders.
 * Handles business logic related to creating, retrieving, updating, and deleting orders.
 */
@Service
public class OrderService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Retrieves all orders.
     *
     * @return a list of all orders
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Retrieves an order by its unique ID.
     *
     * @param orderId the unique identifier of the order
     * @return the order with the specified ID
     * @throws RuntimeException if the order is not found
     */
    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    /**
     * Retrieves a list of orders based on their status.
     *
     * @param status the status of the orders to retrieve
     * @return a list of orders matching the specified status
     */
    public List<Order> getOrdersByStatus(EStatus status) {
        return orderRepository.findByStatus(status);
    }

    /**
     * Creates a new order along with its associated order items.
     *
     * @param request the order request containing customer details, store information, and items
     * @return the newly created order
     */
    @Transactional
    public Order createOrder(OrderRequest request) {
        Order savedOrder = orderRepository.save(Order.builder()
                .customerID(request.getCustomerID())
                .customerEmail(request.getCustomerEmail())
                .storeID(request.getStoreID())
                .totalItemCost(request.getTotalItemCost())
                .isDelivery(request.isDelivery())
                .isCollection(request.isCollection())
                .deliveryFee(request.getDeliveryFee())
                .deliveryAddress(request.getDeliveryAddress())
                .totalCost(request.getTotalCost())
                .status(request.getStatus() != null ? request.getStatus() : EStatus.SUBMITTED)
                .customerLat(request.getCustomerLat())
                .customerLng(request.getCustomerLng())
                .build()
        );

        if (request.getOrderItems() != null && !request.getOrderItems().isEmpty()) {
            List<OrderItem> orderItems = request.getOrderItems().stream().map(itemRequest ->
                    OrderItem.builder()
                            .productId(itemRequest.getProductId())
                            .productName(itemRequest.getProductName())
                            .quantity(itemRequest.getQuantity())
                            .price(itemRequest.getPrice())
                            .order(savedOrder)
                            .build()
            ).collect(Collectors.toList());

            orderItemRepository.saveAll(orderItems);
        }

        return savedOrder;
    }

    /**
     * Updates the status of an existing order.
     *
     * @param orderId the unique identifier of the order
     * @param status the new status to be assigned
     * @return the updated order with the modified status
     */
    public Order updateOrderStatus(String orderId, EStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    /**
     * Deletes an order by its unique ID.
     *
     * @param orderId the unique identifier of the order to be deleted
     */
    public void deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }
}
