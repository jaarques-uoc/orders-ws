package com.jaarquesuoc.shop.orders.controllers;

import com.jaarquesuoc.shop.orders.models.Order;
import com.jaarquesuoc.shop.orders.services.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping("/orders/{id}/")
    public Order getOrder(@PathVariable("id") final String id) {
        return ordersService.getOrder(id);
    }

    @GetMapping("/orders/")
    public List<Order> getOrders() {
        return ordersService.getOrders();
    }
}
