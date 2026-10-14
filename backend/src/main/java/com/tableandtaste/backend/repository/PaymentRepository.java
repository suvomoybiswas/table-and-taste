package com.tableandtaste.backend.repository;

import com.tableandtaste.backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    public List<Payment> findAllByOrderId(Long orderId);

    public Payment findByPaymentId(String paymentId);


}
