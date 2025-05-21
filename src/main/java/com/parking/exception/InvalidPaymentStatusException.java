package com.parking.exception;

public class InvalidPaymentStatusException extends RuntimeException {
    public InvalidPaymentStatusException(String status) {
        super("Invalid payment status: " + status);
    }
}