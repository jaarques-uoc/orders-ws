package com.jaarquesuoc.shop.orders.services;

import com.jaarquesuoc.shop.orders.dtos.NextOrderIdDto;
import com.jaarquesuoc.shop.orders.dtos.OrderDto;
import com.jaarquesuoc.shop.orders.dtos.OrderItemDto;
import com.jaarquesuoc.shop.orders.dtos.ProductDto;
import com.jaarquesuoc.shop.orders.mappers.OrderMapper;
import com.jaarquesuoc.shop.orders.models.Order;
import com.jaarquesuoc.shop.orders.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrdersService {

    private final ProductsService productsService;

    private final OrdersRepository ordersRepository;

    public Optional<OrderDto> getOrderDto(final String id) {
        Optional<OrderDto> optionalOrderDto = ordersRepository.findById(id)
            .map(OrderMapper.INSTANCE::toOrderDto);

        optionalOrderDto.ifPresent(this::populateCartDtoWithProducts);

        return optionalOrderDto;
    }

    public List<OrderDto> getOrderDtos() {
        return ordersRepository.findAll()
            .stream()
            .map(OrderMapper.INSTANCE::toOrderDto)
            .collect(toList());
    }

    public List<OrderDto> getCustomerOrderDtos(final String customerId) {
        return ordersRepository.findAllByCustomerId(customerId)
            .stream()
            .map(OrderMapper.INSTANCE::toOrderDto)
            .collect(toList());
    }

    public Optional<OrderDto> getCustomerOrderDto(final String orderId, final String customerId) {
        Optional<OrderDto> optionalOrderDto = ordersRepository.findByIdAndCustomerId(orderId, customerId)
            .map(OrderMapper.INSTANCE::toOrderDto);

        optionalOrderDto.ifPresent(this::populateCartDtoWithProducts);

        return optionalOrderDto;
    }

    public NextOrderIdDto getNextOrderId(final String customerId) {
        final Long nOrders = ordersRepository.countByCustomerId(customerId);
        return NextOrderIdDto.builder()
            .nextOrderId(generateNextOrderId(customerId, nOrders))
            .build();
    }

    public OrderDto createOrder(final OrderDto orderDto, final String customerId) {
        Order order = OrderMapper.INSTANCE.toOrder(orderDto);

        order.setId(getNextOrderId(customerId).getNextOrderId());
        order.setCustomerId(customerId);
        order.setAmount(calculateAmount(orderDto).orElse(ZERO));

        Order createdOrder = ordersRepository.save(order);

        OrderDto createdOrderDto = OrderMapper.INSTANCE.toOrderDto(createdOrder);

        populateCartDtoWithProducts(createdOrderDto);

        return createdOrderDto;
    }

    private Optional<BigDecimal> calculateAmount(final OrderDto orderDto) {
        return orderDto.getOrderItemDtos().stream()
            .map(this::calculateItemAmount)
            .reduce(BigDecimal::add);
    }

    private BigDecimal calculateItemAmount(final OrderItemDto orderItemDto) {
        return orderItemDto.getProductDto().getPrice().multiply(BigDecimal.valueOf(orderItemDto.getQuantity()));
    }

    private String generateNextOrderId(final String customerId, final Long nOrders) {
        return customerId + "-" + nOrders;
    }

    private void populateCartDtoWithProducts(final OrderDto orderDto) {
        if (orderDto.getOrderItemDtos() == null) {
            return;
        }

        List<String> productIds = orderDto.getOrderItemDtos().stream()
            .map(OrderItemDto::getProductDto)
            .map(ProductDto::getId)
            .collect(toList());

        Map<String, ProductDto> productDtos = productsService.getProducts(productIds);

        orderDto.getOrderItemDtos()
            .forEach(orderItemDto -> orderItemDto.setProductDto(productDtos.get(orderItemDto.getProductDto().getId())));
    }
}
