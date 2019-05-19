package com.jaarquesuoc.shop.orders.services;

import com.jaarquesuoc.shop.orders.dtos.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("customers-ws")
public interface CustomersClient {

    @GetMapping("/customers/{customerId}")
    CustomerDto getCustomer(@PathVariable("customerId") final String customerId);
}
