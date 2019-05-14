package com.jaarquesuoc.shop.orders.repositories;

import com.jaarquesuoc.shop.orders.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdersRepository extends MongoRepository<Order, String> {

    Long countByCustomerId(String customerId);
}
