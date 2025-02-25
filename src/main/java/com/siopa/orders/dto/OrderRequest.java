package com.siopa.orders.dto;

import com.siopa.orders.models.EStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequest {
    private String customerID;

    private String customerEmail;

    private long storeID;

    private BigDecimal totalItemCost;

    private boolean isDelivery;

    private boolean isCollection;

    private BigDecimal deliveryFee;

    private String deliveryAddress;

    private BigDecimal totalCost;

    private EStatus status;

    private double customerLat;

    private double customerLng;
}