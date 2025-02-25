package com.siopa.orders.models;

/**
 * Enum values for order status.
 */
public enum EStatus {
    CANCELLED,
    SUBMITTED,
    PREPARING,
    OUT_FOR_DELIVERY,
    READY_FOR_COLLECTION,
    DELIVERED,
    COLLECTED,
    COMPLETE
}
