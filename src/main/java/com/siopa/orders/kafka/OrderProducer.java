package com.siopa.orders.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siopa.orders.dto.OrderItemRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Kafka Producer that sends product order details to the Kafka topic.
 */
@Service
public class OrderProducer {

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
                kafkaTemplate.send(orderTopic, jsonMessage);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to serialize product order message", e);
            }
        }
    }
}
