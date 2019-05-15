package com.jaarquesuoc.shop.orders.mappers;

import com.jaarquesuoc.shop.orders.dtos.OrderDto;
import com.jaarquesuoc.shop.orders.dtos.OrderItemDto;
import com.jaarquesuoc.shop.orders.models.Order;
import com.jaarquesuoc.shop.orders.models.OrderItem;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "orderItems", target = "orderItemDtos")
    OrderDto toOrderDto(Order order);

    @Mapping(target = "date", ignore = true)
    @Mapping(source = "orderItemDtos", target = "orderItems")
    Order toOrder(OrderDto orderDto);

    @Mapping(source = "productId", target = "productDto.id")
    OrderItemDto toOrderItemDto(OrderItem orderItem);

    @InheritInverseConfiguration
    OrderItem toOrderItem(OrderItemDto orderItemDto);
}
