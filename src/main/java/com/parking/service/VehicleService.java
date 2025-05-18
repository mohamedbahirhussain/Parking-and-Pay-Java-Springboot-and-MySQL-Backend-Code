package com.parking.service;

import com.parking.model.Vehicle;
import com.parking.repository.VehicleRepository;
import com.parking.repository.ParkingRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkingRateRepository parkingRateRepository;

    @Transactional
    public Vehicle recordEntry(String licensePlate) {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(licensePlate);
        vehicle.setEntryTime(LocalDateTime.now());
        vehicle.setParked(true);
        vehicle.setPaid(false);
        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public Vehicle recordExit(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        if (!vehicle.isPaid()) {
            throw new RuntimeException("Payment required before exit");
        }

        vehicle.setExitTime(LocalDateTime.now());
        vehicle.setParked(false);
        return vehicleRepository.save(vehicle);
    }

    public double calculateFee(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        var rate = parkingRateRepository.findByIsActiveTrue()
                .orElseThrow(() -> new RuntimeException("No active parking rate found"));

        LocalDateTime now = LocalDateTime.now();
        long hours = ChronoUnit.HOURS.between(vehicle.getEntryTime(), now);

        double fee = Math.max(
                rate.getMinimumCharge(),
                Math.min(
                        (hours + 1) * rate.getHourlyRate(),
                        rate.getDayMaximum()
                )
        );

        return Math.round(fee * 100.0) / 100.0;
    }

    @Transactional
    public Vehicle processPayment(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        double fee = calculateFee(licensePlate);
        vehicle.setPaymentAmount(fee);
        vehicle.setPaid(true);
        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public Vehicle toggleBlocklist(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate)
                .orElseGet(() -> {
                    Vehicle v = new Vehicle();
                    v.setLicensePlate(licensePlate);
                    v.setEntryTime(LocalDateTime.now());
                    v.setParked(false);
                    return v;
                });

        vehicle.setBlocked(!vehicle.isBlocked());
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getParkedVehicles() {
        return vehicleRepository.findByIsParkedTrue();
    }

    public List<Vehicle> getUnpaidVehicles() {
        return vehicleRepository.findUnpaidParkedVehicles();
    }

    public long getOccupiedSpaces() {
        return vehicleRepository.countParkedVehicles();
    }
}