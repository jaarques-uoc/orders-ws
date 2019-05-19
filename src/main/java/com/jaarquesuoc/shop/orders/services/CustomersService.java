package com.jaarquesuoc.shop.orders.services;

import com.jaarquesuoc.shop.orders.dtos.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomersService {

    private final CustomersClient customersClient;

    public CustomerDto getCustomerDto(final String customerId) {
        return customersClient.getCustomer(customerId);
    }
}
