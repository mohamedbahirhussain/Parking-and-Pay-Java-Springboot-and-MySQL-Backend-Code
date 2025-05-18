package com.parking.repository;

import com.parking.model.ParkingRate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ParkingRateRepository extends JpaRepository<ParkingRate, Long> {
    Optional<ParkingRate> findByIsActiveTrue();
}