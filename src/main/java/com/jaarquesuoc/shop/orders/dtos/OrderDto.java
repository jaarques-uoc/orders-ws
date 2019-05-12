package com.jaarquesuoc.shop.orders.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderDto {

    private String id;

    private BigDecimal amount;

    private LocalDateTime date;

    private String customerId;

    private List<OrderItemDto> orderItemDtos;
}
