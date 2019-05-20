package com.jaarquesuoc.shop.orders.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEventDto {

    private String id;

    private String orderId;

    private LocalDateTime date;

    private OrderStatus status;
}
