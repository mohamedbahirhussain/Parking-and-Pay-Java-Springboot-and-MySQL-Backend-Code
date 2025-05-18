package com.parking.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "parking_rates")
public class ParkingRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double hourlyRate;

    private Double minimumCharge;

    private Double dayMaximum;

    @Column(nullable = false)
    private boolean isActive = true;
}