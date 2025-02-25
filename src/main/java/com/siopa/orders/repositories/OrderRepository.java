package com.siopa.orders.repositories;

import com.siopa.orders.models.EStatus;
import com.siopa.orders.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByCustomerID(UUID customerID);
    List<Order> findByStatus(EStatus status);
}