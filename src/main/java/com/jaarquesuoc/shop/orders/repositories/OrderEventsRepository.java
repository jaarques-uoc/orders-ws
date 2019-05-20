package com.jaarquesuoc.shop.orders.repositories;

import com.jaarquesuoc.shop.orders.models.OrderEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderEventsRepository extends MongoRepository<OrderEvent, String> {

    List<OrderEvent> findAllByOrderId(final String orderId);
}
