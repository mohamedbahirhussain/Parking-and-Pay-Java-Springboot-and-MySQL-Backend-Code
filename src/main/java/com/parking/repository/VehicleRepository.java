package com.parking.repository;

import com.parking.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByLicensePlate(String licensePlate);

    List<Vehicle> findByIsParkedTrue();

    @Query("SELECT v FROM Vehicle v WHERE v.isParked = true AND v.isPaid = false")
    List<Vehicle> findUnpaidParkedVehicles();

    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.isParked = true")
    long countParkedVehicles();
}