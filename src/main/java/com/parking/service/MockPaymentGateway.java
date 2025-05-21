package com.parking.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MockPaymentGateway implements PaymentGateway {

    @Override
    public boolean charge(BigDecimal amount) {
        // Simulate always successful transaction
        return true;
    }
}
