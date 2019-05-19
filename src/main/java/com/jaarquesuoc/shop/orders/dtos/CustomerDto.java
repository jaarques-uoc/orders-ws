package com.jaarquesuoc.shop.orders.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private String id;

    private String email;

    private String password;

    private String fullName;

    private String address;

    private String country;

    private LocalDateTime date;

    private CustomerType type;
}
