package com.siopa.orders.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @UuidGenerator
    @Column(name = "order_id", updatable = false, nullable = false)
    private UUID orderId;
    @Column
    private UUID customerID;
    @Column
    private String customerEmail;
    @Column
    private long storeID;
    @Column
    private BigDecimal totalItemCost;
    @Column
    private boolean isDelivery;
    @Column
    private boolean isCollection;
    @Column
    private BigDecimal deliveryFee;
    @Column
    private String deliveryAddress;
    @Column
    private BigDecimal totalCost;
    @Column
    @Enumerated(EnumType.STRING)
    private EStatus status;
    @Column
    private double customerLat;
    @Column
    private double customerLng;
    @CreationTimestamp
    private Date crtdTimeStamp;
    @UpdateTimestamp
    private Date uptdTimeStamp;
}
