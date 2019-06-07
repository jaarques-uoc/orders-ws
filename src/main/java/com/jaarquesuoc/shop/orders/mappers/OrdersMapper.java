package com.jaarquesuoc.shop.orders.mappers;

import com.jaarquesuoc.shop.orders.dtos.OrderDto;
import com.jaarquesuoc.shop.orders.dtos.OrderEventDto;
import com.jaarquesuoc.shop.orders.dtos.OrderItemDto;
import com.jaarquesuoc.shop.orders.models.Order;
import com.jaarquesuoc.shop.orders.models.OrderEvent;
import com.jaarquesuoc.shop.orders.models.OrderItem;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrdersMapper {

    OrdersMapper INSTANCE = Mappers.getMapper(OrdersMapper.class);

    @Mapping(source = "orderItems", target = "orderItemDtos")
    OrderDto toOrderDto(Order order);

    @Mapping(target = "date", ignore = true)
    @Mapping(source = "orderItemDtos", target = "orderItems")
    Order toOrder(OrderDto orderDto);

    List<OrderDto> toOrderDtos(List<Order> order);

    @Mapping(source = "productId", target = "productDto.id")
    OrderItemDto toOrderItemDto(OrderItem orderItem);

    @InheritInverseConfiguration
    OrderItem toOrderItem(OrderItemDto orderItemDto);

    OrderEventDto toOrderEventDto(OrderEvent orderEvent);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    OrderEvent toOrderEvent(OrderEventDto orderEventDto);

    List<OrderEventDto> toOrderEventDtos(List<OrderEvent> orderEvents);
}
