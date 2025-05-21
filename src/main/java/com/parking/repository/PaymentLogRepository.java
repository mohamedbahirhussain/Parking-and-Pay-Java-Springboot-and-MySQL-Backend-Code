package com.parking.repository;

import com.parking.model.PaymentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentLogRepository extends JpaRepository<PaymentLog, Long> {
    List<PaymentLog> findByPaymentId(Long paymentId);
}
