package com.jaarquesuoc.shop.orders.models;

import com.jaarquesuoc.shop.orders.dtos.OrderStatus;
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
public class OrderEvent {

    @Id
    private String id;

    private String orderId;

    @Builder.Default()
    private LocalDateTime date = LocalDateTime.now();

    private OrderStatus status;
}
