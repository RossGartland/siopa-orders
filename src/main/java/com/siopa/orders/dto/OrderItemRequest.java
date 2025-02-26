package com.siopa.orders.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO for representing an order item in an order request.
 * This class is used when creating or updating an order.
 */
@Data
public class OrderItemRequest {

    /**
     * The unique identifier of the product.
     */
    private String productId;

    /**
     * The name of the product.
     */
    private String productName;

    /**
     * The quantity of the product ordered.
     */
    private int quantity;

    /**
     * The price of the product.
     */
    private BigDecimal price;
}
