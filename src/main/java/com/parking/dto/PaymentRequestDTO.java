package com.parking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class PaymentRequestDTO {

    @NotNull(message = "Parking entry ID is required")
    private Long parkingEntryId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    public Long getParkingEntryId() {
        return parkingEntryId;
    }

    public void setParkingEntryId(Long parkingEntryId) {
        this.parkingEntryId = parkingEntryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
