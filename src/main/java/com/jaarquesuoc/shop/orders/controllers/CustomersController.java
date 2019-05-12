package com.jaarquesuoc.shop.orders.controllers;

import com.jaarquesuoc.shop.orders.dtos.NextOrderIdDto;
import com.jaarquesuoc.shop.orders.dtos.OrderDto;
import com.jaarquesuoc.shop.orders.services.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomersController {

    private final OrdersService ordersService;

    @GetMapping("/customers/{customerId}/orders")
    public List<OrderDto> getCustomerOrders(@PathVariable("customerId") final String customerId) {
        return ordersService.getCustomerOrders(customerId);
    }

    @GetMapping("/customers/{customerId}/nextOrderId")
    public NextOrderIdDto getNextOrderId(@PathVariable("customerId") final String customerId) {
        return ordersService.getNextOrderId(customerId);
    }

}