package com.siopa.orders.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the product order message sent to Kafka.
 * Contains only the product ID and quantity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderMessage {
    private String productId;
    private int quantity;
}
