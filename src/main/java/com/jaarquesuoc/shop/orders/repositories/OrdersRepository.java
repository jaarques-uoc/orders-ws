package com.jaarquesuoc.shop.orders.repositories;

import com.jaarquesuoc.shop.orders.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends MongoRepository<Order, String> {

    Long countByCustomerId(final String customerId);

    List<Order> findAllByCustomerId(final String customerId);

    Optional<Order> findByIdAndCustomerId(final String orderId, final String customerId);
}
