package com.siopa.orders.services;

import com.siopa.orders.dto.OrderRequest;
import com.siopa.orders.kafka.OrderProducer;
import com.siopa.orders.models.EStatus;
import com.siopa.orders.models.Order;
import com.siopa.orders.models.OrderItem;
import com.siopa.orders.repositories.OrderItemRepository;
import com.siopa.orders.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProducer orderProducer;

    /**
     * Retrieves all orders.
     *
     * @return a list of all orders
     */
    public List<Order> getAllOrders() {
        logger.info("Fetching all orders");
        List<Order> orders = orderRepository.findAll();
        logger.debug("Retrieved {} orders", orders.size());
        return orders;
    }

    /**
     * Retrieves an order by its unique ID.
     *
     * @param orderId the unique identifier of the order
     * @return the order with the specified ID
     * @throws RuntimeException if the order is not found
     */
    public Order getOrderById(String orderId) {
        logger.info("Fetching order with ID: {}", orderId);
        return orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    logger.error("Order with ID {} not found", orderId);
                    return new RuntimeException("Order not found");
                });
    }

    /**
     * Retrieves a list of orders based on their status.
     *
     * @param status the status of the orders to retrieve
     * @return a list of orders matching the specified status
     */
    public List<Order> getOrdersByStatus(EStatus status) {
        logger.info("Fetching orders with status: {}", status);
        List<Order> orders = orderRepository.findByStatus(status);
        logger.debug("Found {} orders with status {}", orders.size(), status);
        return orders;
    }

    /**
     * Creates a new order along with its associated order items.
     * Sends order item quantities to Kafka for inventory management.
     *
     * @param request the order request containing customer details, store information, and items
     * @return the newly created order
     */
    @Transactional
    public Order createOrder(OrderRequest request) {
        logger.info("Creating a new order for customer ID: {}", request.getCustomerID());

        Order savedOrder = orderRepository.save(Order.builder()
                .customerID(request.getCustomerID())
                .customerEmail(request.getCustomerEmail())
                .forename(request.getForename())
                .surname(request.getSurname())
                .phoneNumber(request.getPhoneNumber())
                .billingAddress(request.getBillingAddress())
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

        logger.info("Order {} created successfully", savedOrder.getOrderId());

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
            logger.info("Saved {} order items for order ID: {}", orderItems.size(), savedOrder.getOrderId());
        }

        orderProducer.sendOrder(request.getOrderItems());
        logger.info("Order items sent to Kafka for inventory management");

        return savedOrder;
    }

    /**
     * Updates the status of an existing order.
     *
     * @param orderId the unique identifier of the order
     * @param status  the new status to be assigned
     * @return the updated order with the modified status
     */
    public Order updateOrderStatus(String orderId, EStatus status) {
        logger.info("Updating order ID {} to status {}", orderId, status);
        Order order = getOrderById(orderId);
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        logger.info("Order ID {} updated to status {}", orderId, status);
        return updatedOrder;
    }

    /**
     * Deletes an order by its unique ID.
     *
     * @param orderId the unique identifier of the order to be deleted
     */
    public void deleteOrder(String orderId) {
        logger.warn("Deleting order with ID: {}", orderId);
        orderRepository.deleteById(orderId);
        logger.info("Order ID {} deleted successfully", orderId);
    }
}
