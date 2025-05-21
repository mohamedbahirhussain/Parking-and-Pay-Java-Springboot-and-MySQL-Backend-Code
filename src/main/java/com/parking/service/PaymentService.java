package com.parking.service;

import com.parking.dto.PaymentResponseDTO;
import com.parking.exception.InvalidPaymentStatusException;
import com.parking.exception.PaymentNotFoundException;
import com.parking.model.Payment;
import com.parking.model.PaymentLog;
import com.parking.repository.PaymentLogRepository;
import com.parking.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentLogRepository paymentLogRepository;

    @Autowired
    private MockPaymentGateway paymentGateway;

    public Payment initiatePayment(Long parkingEntryId, BigDecimal amount) {
        Payment payment = new Payment();
        payment.setParkingEntryId(parkingEntryId);
        payment.setAmount(amount);
        payment.setStatus("PENDING");

        Payment savedPayment = paymentRepository.save(payment);

        PaymentLog log = new PaymentLog();
        log.setPayment(savedPayment);
        log.setMessage("Payment initiated with amount: " + amount);
        log.setStatus("INITIATED");
        paymentLogRepository.save(log);

        return savedPayment;
    }

    public Payment completePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException(paymentId));

        if (!"PENDING".equals(payment.getStatus())) {
            throw new InvalidPaymentStatusException(payment.getStatus());
        }

        boolean success = paymentGateway.charge(payment.getAmount());

        if (success) {
            payment.setStatus("COMPLETED");
            log(payment, "Payment completed successfully");
        } else {
            payment.setStatus("FAILED");
            log(payment, "Payment failed via test gateway");
        }

        return paymentRepository.save(payment);
    }

    private void log(Payment payment, String message) {
        PaymentLog log = new PaymentLog();
        log.setPayment(payment);
        log.setMessage(message);
        log.setStatus("FINISHED");
        paymentLogRepository.save(log);
    }

    public PaymentResponseDTO toPaymentResponseDTO(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getParkingEntryId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getCreatedAt()
        );
    }

}
