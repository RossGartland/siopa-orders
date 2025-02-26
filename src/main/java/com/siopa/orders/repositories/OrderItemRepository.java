package com.siopa.orders.repositories;

import com.siopa.orders.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing `OrderItem` entities.
 * Provides CRUD operations through JpaRepository.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
}
