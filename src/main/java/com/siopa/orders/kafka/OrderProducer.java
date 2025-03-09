package com.siopa.orders.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siopa.orders.dto.OrderItemRequest;
import com.siopa.orders.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Kafka Producer that sends product order details to the Kafka topic.
 */
@Service
public class OrderProducer {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Value("${spring.kafka.topic.order}")
    private String orderTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OrderProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Sends product ID and quantity as a JSON message to Kafka.
     *
     * @param orderItems the list of order items
     */
    public void sendOrder(List<OrderItemRequest> orderItems) {
        for (OrderItemRequest item : orderItems) {
            try {
                ProductOrderMessage message = new ProductOrderMessage(item.getProductId(), item.getQuantity());
                String jsonMessage = objectMapper.writeValueAsString(message);
                logger.debug("Sending message to Kafka: {}", jsonMessage);
                kafkaTemplate.send(orderTopic, jsonMessage);
                logger.info("Successfully sent order item with Product ID: {} and Quantity: {}", item.getProductId(),
                        item.getQuantity());
            } catch (JsonProcessingException e) {
                logger.info("Successfully sent order item with Product ID: {} and Quantity: {}", item.getProductId(),
                        item.getQuantity());
                throw new RuntimeException("Failed to serialize product order message", e);
            }
        }
    }
}
