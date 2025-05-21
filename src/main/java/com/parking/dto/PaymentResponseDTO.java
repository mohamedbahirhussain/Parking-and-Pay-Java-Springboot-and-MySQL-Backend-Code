package com.parking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponseDTO {

    private Long id;
    private Long parkingEntryId;
    private BigDecimal amount;
    private String status;
    private LocalDateTime createdAt;

    public PaymentResponseDTO() {}

    public PaymentResponseDTO(Long id, Long parkingEntryId, BigDecimal amount, String status, LocalDateTime createdAt) {
        this.id = id;
        this.parkingEntryId = parkingEntryId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

