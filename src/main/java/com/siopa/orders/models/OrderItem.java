package com.siopa.orders.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;

/**
 * Entity representing an individual item in an order.
 * This class stores product details, quantity, and price for each item in an order.
 */
@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    /**
     * Unique identifier for the order item.
     * This is stored as a string but expected to be in UUID format.
     */
    @Id
    @UuidGenerator
    @Column(name = "order_item_id", updatable = false, nullable = false)
    private String orderItemId;

    /**
     * Unique identifier for the product associated with this order item.
     * This is stored as a string but expected to be in UUID format.
     */
    @Column(nullable = false)
    private String productId;

    /**
     * Name of the product.
     */
    @Column(nullable = false)
    private String productName;

    /**
     * Quantity of the product ordered.
     */
    @Column(nullable = false)
    private int quantity;

    /**
     * Price of the product.
     */
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * The order that this item belongs to.
     * This establishes a many-to-one relationship with the `Order` entity.
     */
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;
}
