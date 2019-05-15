package com.jaarquesuoc.shop.orders.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String id;

    private BigDecimal amount;

    @Builder.Default()
    private LocalDateTime date = LocalDateTime.now();

    private String customerId;

    private List<OrderItem> orderItems;
}
