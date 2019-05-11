package com.jaarquesuoc.shop.orders.services;

import com.jaarquesuoc.shop.orders.models.NextOrderId;
import com.jaarquesuoc.shop.orders.models.Order;
import com.jaarquesuoc.shop.orders.models.OrderItem;
import com.jaarquesuoc.shop.orders.models.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class OrdersService {

    public List<Order> getOrders() {
        return IntStream.range(0, 30)
            .mapToObj(i -> buildOrder(String.valueOf(i)))
            .collect(Collectors.toList());
    }

    public List<Order> getCustomerOrders(final String customerId) {
        return IntStream.range(0, 30)
            .mapToObj(i -> buildOrder(String.valueOf(i), customerId))
            .collect(Collectors.toList());
    }

    public Order getOrder(final String id) {
        return buildOrder(id);
    }

    public NextOrderId getNextOrderId(final String customerId) {
        return NextOrderId.builder()
            .nextOrderId(customerId + "-" + 0)
            .build();
    }

    private Order buildOrder(final String id) {
        return buildOrder(id, id);
    }

    private Order buildOrder(final String id, final String customerId) {
        return Order.builder()
            .id(id)
            .amount(BigDecimal.valueOf(12.56))
            .date(LocalDateTime.now())
            .customerId(customerId)
            .orderItems(buildOrderItems())
            .build();
    }

    private List<OrderItem> buildOrderItems() {
        return IntStream.range(0, 5)
            .mapToObj(i -> buildOrderItem(String.valueOf(i)))
            .collect(Collectors.toList());
    }

    private OrderItem buildOrderItem(final String id) {
        return OrderItem.builder()
            .product(Product.builder()
                .id(id)
                .name("Item name " + id)
                .description("Some description")
                .price(BigDecimal.valueOf(2.23D))
                .imageUrl("https://static.cardmarket.com/img/548dd39417c935651fbd98c3ee6d5951/items/1/WAR/372131.jpg")
                .build())
            .quantity(2)
            .build();
    }
}
