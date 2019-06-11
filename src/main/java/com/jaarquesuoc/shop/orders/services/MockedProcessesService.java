package com.jaarquesuoc.shop.orders.services;

import org.springframework.stereotype.Service;

@Service
public class MockedProcessesService {

    public Void processPayment() {
        return mockService();
    }

    public Void prepareShipment() {
        return mockService();
    }

    public Void sendOrder() {
        return mockService();
    }

    private Void mockService() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
