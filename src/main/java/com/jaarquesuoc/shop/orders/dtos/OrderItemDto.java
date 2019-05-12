package com.jaarquesuoc.shop.orders.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDto {

    private ProductDto productDto;

    private int quantity;
}
