package com.siopa.orders.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Entity representing an order in the system.
 * This class stores all relevant details about an order, including customer and store information,
 * pricing, delivery details, and status.
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    /**
     * Unique identifier for the order.
     * This is stored as a string but expected to be in UUID format.
     */
    @Id
    @UuidGenerator
    @Column(name = "order_id", updatable = false, nullable = false)
    private String orderId;

    /**
     * Unique identifier for the customer placing the order.
     * This is stored as a string but expected to be in UUID format.
     */
    @Column(nullable = false)
    private String customerID;

    /**
     * Email address of the customer.
     */
    @Column(nullable = false)
    private String customerEmail;


    /**
     * The forename of the customer.
     */
    @Column
    private String forename;


    /**
     * The surname of the customer.
     */
    @Column
    private String surname;

    /**
     * The phone number of the customer.
     */
    @Column
    private String phoneNumber;

    /**
     * Unique identifier for the store where the order is placed.
     * This is stored as a string but expected to be in UUID format.
     */
    @Column(nullable = false)
    private String storeID;

    /**
     * Total cost of all items in the order.
     */
    @Column(nullable = false)
    private BigDecimal totalItemCost;

    /**
     * Indicates whether the order is for delivery.
     */
    @Column(nullable = false)
    private boolean isDelivery;

    /**
     * Indicates whether the order is for collection (pickup).
     */
    @Column(nullable = false)
    private boolean isCollection;

    /**
     * The delivery fee associated with the order, if applicable.
     */
    @Column
    private BigDecimal deliveryFee;

    /**
     * The delivery address for the order.
     */
    @Column
    private String deliveryAddress;

    /**
     * The billing address of the customer.
     */
    @Column
    private String billingAddress;

    /**
     * The total cost of the order, including items, delivery, and any additional fees.
     */
    @Column(nullable = false)
    private BigDecimal totalCost;

    /**
     * The current status of the order.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EStatus status;

    /**
     * The latitude of the customer's location (for delivery tracking purposes).
     */
    @Column(nullable = false)
    private double customerLat;

    /**
     * The longitude of the customer's location (for delivery tracking purposes).
     */
    @Column(nullable = false)
    private double customerLng;

    /**
     * Timestamp indicating when the order was created.
     * This is automatically generated.
     */
    @CreationTimestamp
    private Date crtdTimeStamp;

    /**
     * Timestamp indicating when the order was last updated.
     * This is automatically updated on modifications.
     */
    @UpdateTimestamp
    private Date uptdTimeStamp;

    /**
     * List of items included in the order.
     * This represents a one-to-many relationship between an order and its items.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;
}
