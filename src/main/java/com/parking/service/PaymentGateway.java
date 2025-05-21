package com.parking.service;

import java.math.BigDecimal;

public interface PaymentGateway {
    boolean charge(BigDecimal amount);
}
