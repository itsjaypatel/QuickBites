package com.itsjaypatel.quickbites.repositories;

import com.itsjaypatel.quickbites.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    Optional<Payment> findByOrderId(String orderId);

    boolean existsByTransactionId(String transactionId);
}
