package com.jaarquesuoc.shop.orders.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {

    private Product product;

    private int quantity;
}
