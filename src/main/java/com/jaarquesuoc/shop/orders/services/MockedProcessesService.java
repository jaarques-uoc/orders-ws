package com.jaarquesuoc.shop.orders.services;

import org.springframework.stereotype.Service;

@Service
public class MockedProcessesService {

    public boolean processPayment() {
        return mockService();
    }

    public boolean prepareShipment() {
        return mockService();
    }

    public boolean sendOrder() {
        return mockService();
    }

    private boolean mockService() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }
}
