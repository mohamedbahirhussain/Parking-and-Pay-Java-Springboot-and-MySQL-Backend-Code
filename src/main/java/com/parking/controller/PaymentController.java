package com.parking.controller;

import com.parking.dto.PaymentRequestDTO;
import com.parking.dto.PaymentResponseDTO;
import com.parking.model.Payment;
import com.parking.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping()
    public ResponseEntity<PaymentResponseDTO> initiatePayment(@Valid @RequestBody PaymentRequestDTO paymentDTO) {
        Payment payment = paymentService.initiatePayment(paymentDTO.getParkingEntryId(), paymentDTO.getAmount());
        return ResponseEntity.ok(paymentService.toPaymentResponseDTO(payment));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<PaymentResponseDTO> completePayment(@PathVariable Long id) {
        Payment payment = paymentService.completePayment(id);
        return ResponseEntity.ok(paymentService.toPaymentResponseDTO(payment));
    }
}

