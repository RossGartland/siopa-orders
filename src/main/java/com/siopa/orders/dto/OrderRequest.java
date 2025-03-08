package com.siopa.orders.dto;

import com.siopa.orders.models.EStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for creating or updating an order.
 * This class contains all the necessary details to process an order.
 */
@Data
public class OrderRequest {

    /**
     * The unique identifier of the customer placing the order.
     * Expected to be in UUID format.
     */
    private String customerID;

    /**
     * The email address of the customer.
     */
    private String customerEmail;

    /**
     * The forename of the customer.
     */
    private String forename;


    /**
     * The surname of the customer.
     */
    private String surname;

    /**
     * The unique identifier of the store where the order is placed.
     * Expected to be in UUID format.
     */
    private String storeID;

    /**
     * The total cost of all items in the order.
     */
    private BigDecimal totalItemCost;

    /**
     * Indicates whether the order is for delivery.
     */
    private boolean isDelivery;

    /**
     * Indicates whether the order is for collection (pickup).
     */
    private boolean isCollection;

    /**
     * The delivery fee associated with the order, if applicable.
     */
    private BigDecimal deliveryFee;

    /**
     * The address where the order should be delivered.
     */
    private String deliveryAddress;

    /**
     * The billing address of the customer.
     */
    private String billingAddress;

    /**
     * The total cost of the order, including items and any additional fees.
     */
    private BigDecimal totalCost;

    /**
     * The current status of the order.
     */
    private EStatus status;

    /**
     * The latitude of the customer's location (for delivery purposes).
     */
    private double customerLat;

    /**
     * The longitude of the customer's location (for delivery purposes).
     */
    private double customerLng;

    /**
     * The list of items included in the order.
     */
    private List<OrderItemRequest> orderItems;
}
