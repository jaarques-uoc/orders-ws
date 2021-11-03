package com.jaarquesuoc.shop.orders.controllers;

import com.jaarquesuoc.shop.orders.dtos.InitialisationDto;
import com.jaarquesuoc.shop.orders.services.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.jaarquesuoc.shop.orders.dtos.InitialisationDto.InitialisationStatus.OK;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InitialisationController {

    private final OrdersService ordersService;

    @GetMapping("/init")
    public InitialisationDto initialiseDB() {
        ordersService.cleanDb();

        return InitialisationDto.builder()
                .initialisationStatus(OK)
                .metadata(ordersService.getAllOrderDtos())
                .build();
    }
}
