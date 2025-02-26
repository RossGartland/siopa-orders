package com.siopa.orders.repositories;

import com.siopa.orders.models.EStatus;
import com.siopa.orders.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing `Order` entities.
 * Provides CRUD operations and custom queries for retrieving orders by customer ID and status.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    /**
     * Retrieves a list of orders by the given customer ID.
     *
     * @param customerID the unique identifier of the customer
     * @return a list of orders belonging to the specified customer
     */
    List<Order> findByCustomerID(String customerID);

    /**
     * Retrieves a list of orders by their status.
     *
     * @param status the status of the orders
     * @return a list of orders with the specified status
     */
    List<Order> findByStatus(EStatus status);
}
