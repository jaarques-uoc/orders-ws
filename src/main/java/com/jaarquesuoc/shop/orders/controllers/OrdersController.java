package com.jaarquesuoc.shop.orders.controllers;

import com.jaarquesuoc.shop.orders.dtos.OrderDto;
import com.jaarquesuoc.shop.orders.dtos.OrderEventDto;
import com.jaarquesuoc.shop.orders.services.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping("/orders/{id}")
    public OrderDto getOrder(@PathVariable("id") final String id) {
        return ordersService.getOrderDto(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/orders/")
    public List<OrderDto> getOrders() {
        return ordersService.getAllOrderDtos();
    }

    @GetMapping("/orders/{id}/events")
    public List<OrderEventDto> getOrderEvents(@PathVariable("id") final String id) {
        return ordersService.getAllOrderEventDtosByOrderId(id);
    }
}
