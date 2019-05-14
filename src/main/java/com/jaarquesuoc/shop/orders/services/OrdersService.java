package com.jaarquesuoc.shop.orders.services;

import com.jaarquesuoc.shop.orders.dtos.NextOrderIdDto;
import com.jaarquesuoc.shop.orders.dtos.OrderDto;
import com.jaarquesuoc.shop.orders.dtos.OrderItemDto;
import com.jaarquesuoc.shop.orders.dtos.ProductDto;
import com.jaarquesuoc.shop.orders.repositories.OrdersRepository;
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

    private final OrdersRepository ordersRepository;

    public List<OrderDto> getOrders() {
        return IntStream.range(0, 30)
            .mapToObj(i -> buildViewOrder(String.valueOf(i)))
            .collect(toList());
    }

    public List<OrderDto> getCustomerOrders(final String customerId) {
        return IntStream.range(0, 30)
            .mapToObj(i -> buildViewOrder(String.valueOf(i), customerId))
            .collect(toList());
    }

    public OrderDto getOrder(final String id) {
        return buildOrder(id);
    }

    public NextOrderIdDto getNextOrderId(final String customerId) {
        final Long nOrders = ordersRepository.countByCustomerId(customerId);
        return NextOrderIdDto.builder()
            .nextOrderId(generateNextOrderId(customerId, nOrders))
            .build();
    }

    private String generateNextOrderId(final String customerId, final Long nOrders) {
        return customerId + "-" + nOrders;
    }

    private OrderDto buildOrder(final String id) {
        return buildOrder(id, id);
    }

    private OrderDto buildOrder(final String id, final String customerId) {
        return OrderDto.builder()
            .id(id)
            .amount(BigDecimal.valueOf(12.56))
            .date(LocalDateTime.now())
            .customerId(customerId)
            .orderItemDtos(buildOrderItems())
            .build();
    }

    private OrderDto buildViewOrder(final String id) {
        return buildViewOrder(id, id);
    }

    private OrderDto buildViewOrder(final String id, final String customerId) {
        return OrderDto.builder()
            .id(id)
            .amount(BigDecimal.valueOf(12.56))
            .date(LocalDateTime.now())
            .customerId(customerId)
            .build();
    }

    private List<OrderItemDto> buildOrderItems() {
        List<String> productIds = IntStream.range(0, 5)
            .mapToObj(String::valueOf)
            .collect(toList());

        List<ProductDto> productDtos = productsService.getProducts(productIds);

        return productDtos.stream()
            .map(this::buildOrderItem)
            .collect(toList());
    }

    private OrderItemDto buildOrderItem(final ProductDto productDto) {
        return OrderItemDto.builder()
            .productDto(productDto)
            .quantity(2)
            .build();
    }
}
