package com.jaarquesuoc.shop.orders.services;

import com.jaarquesuoc.shop.orders.dtos.NextOrderId;
import com.jaarquesuoc.shop.orders.dtos.Order;
import com.jaarquesuoc.shop.orders.dtos.OrderItem;
import com.jaarquesuoc.shop.orders.dtos.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrdersService {

    private final ProductsService productsService;

    public List<Order> getOrders() {
        return IntStream.range(0, 30)
            .mapToObj(i -> buildOrder(String.valueOf(i)))
            .collect(toList());
    }

    public List<Order> getCustomerOrders(final String customerId) {
        return IntStream.range(0, 30)
            .mapToObj(i -> buildOrder(String.valueOf(i), customerId))
            .collect(toList());
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
        List<String> productIds = IntStream.range(0, 5)
            .mapToObj(String::valueOf)
            .collect(toList());

        List<Product> products = productsService.getProducts(productIds);

        return products.stream()
            .map(this::buildOrderItem)
            .collect(toList());
    }

    private OrderItem buildOrderItem(final Product product) {
        return OrderItem.builder()
            .product(product)
            .quantity(2)
            .build();
    }
}
