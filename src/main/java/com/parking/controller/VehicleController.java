package com.parking.controller;

import com.parking.model.Vehicle;
import com.parking.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "*")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/entry")
    public ResponseEntity<Vehicle> recordEntry(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.recordEntry(vehicle.getLicensePlate()));
    }

    @PostMapping("/exit")
    public ResponseEntity<Vehicle> recordExit(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.recordExit(vehicle.getLicensePlate()));
    }

    @GetMapping("/fee")
    public ResponseEntity<Map<String, Double>> calculateFee(@RequestParam String licensePlate) {
        double fee = vehicleService.calculateFee(licensePlate);
        return ResponseEntity.ok(Map.of("fee", fee));
    }

    @PostMapping("/payment")
    public ResponseEntity<Vehicle> processPayment(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.processPayment(vehicle.getLicensePlate()));
    }

    @PostMapping("/blocklist/toggle")
    public ResponseEntity<Vehicle> toggleBlocklist(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.toggleBlocklist(vehicle.getLicensePlate()));
    }

    @GetMapping("/parked")
    public ResponseEntity<List<Vehicle>> getParkedVehicles() {
        return ResponseEntity.ok(vehicleService.getParkedVehicles());
    }

    @GetMapping("/unpaid")
    public ResponseEntity<List<Vehicle>> getUnpaidVehicles() {
        return ResponseEntity.ok(vehicleService.getUnpaidVehicles());
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getParkingStatus() {
        long occupiedSpaces = vehicleService.getOccupiedSpaces();
        return ResponseEntity.ok(Map.of(
                "occupiedSpaces", occupiedSpaces,
                "availableSpaces", 100 - occupiedSpaces
        ));
    }
}
