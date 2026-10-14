package com.tableandtaste.backend.controller;

import com.tableandtaste.backend.dto.CreatePaymentRequest;
import com.tableandtaste.backend.dto.CreatePaymentResponse;
import com.tableandtaste.backend.dto.PaymentStatusRequest;
import com.tableandtaste.backend.entity.Payment;
import com.tableandtaste.backend.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment postPayment(@RequestBody Payment payment){
        return paymentService.savePayment(payment);
    }

    @GetMapping("/{paymentId}")
    public Payment getPayment(@PathVariable String paymentId){
        return paymentService.getPayment(paymentId);
    }

    @GetMapping("/order/{orderId}")
    public List<Payment> getAllPayments(@PathVariable Long orderId){
        return paymentService.getAllPayments(orderId);
    }

    @PutMapping("/{paymentId}/status")
    public Payment putPaymentStatus(
            @PathVariable String paymentId,
            @RequestBody PaymentStatusRequest request) {

        return paymentService.updatePaymentStatus(
                paymentId,
                 request.getPaymentStatus()
        );
    }

    @PostMapping("/create")
    public CreatePaymentResponse createPayment(
            @RequestBody CreatePaymentRequest request) {

        return paymentService.createPayment(request);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(@RequestBody String payload,
                                              HttpServletRequest request) {
        return paymentService.handleWebhook(payload, request);

    }
}
