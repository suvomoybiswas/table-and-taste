package com.tableandtaste.backend.service;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.ApiResource;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import com.tableandtaste.backend.config.StripeConfig;
import com.tableandtaste.backend.dto.CreatePaymentRequest;
import com.tableandtaste.backend.dto.CreatePaymentResponse;
import com.tableandtaste.backend.entity.Order;
import com.tableandtaste.backend.entity.Payment;
import com.tableandtaste.backend.exception.OrderNotFoundException;
import com.tableandtaste.backend.repository.OrderRepository;
import com.tableandtaste.backend.repository.PaymentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final StripeConfig stripeConfig;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository, StripeConfig stripeConfig) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.stripeConfig = stripeConfig;
    }

    public Payment savePayment(Payment payment){
        return paymentRepository.save(payment);
    }

    public Payment getPayment(String paymentId){
        return paymentRepository.findByPaymentId(paymentId);
    }

    public List<Payment> getAllPayments(Long orderId){
        return paymentRepository.findAllByOrderId(orderId);
    }

    public Payment updatePaymentStatus(
            String paymentId,
            String paymentStatus) {

        Payment payment =
                paymentRepository.findByPaymentId(paymentId);

        payment.setPaymentStatus(paymentStatus);

        return paymentRepository.save(payment);
    }

    public BigDecimal getOrderTotal(Long orderId) {
        return orderRepository.calculateOrderTotal(orderId);
    }

    public CreatePaymentResponse createPayment(CreatePaymentRequest request) {

        Long orderId = request.getOrderId();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order " + orderId + " was not found"));

        BigDecimal amount =
                orderRepository.calculateOrderTotal(orderId);

        long amountInCents = amount
                .multiply(BigDecimal.valueOf(100))
                .longValueExact();

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(amountInCents)
                        .setCurrency("usd")
                        .build();

        PaymentIntent paymentIntent;
        try {
             paymentIntent =
                    PaymentIntent.create(params);
        }catch (StripeException e) {
           throw new RuntimeException("Stripe payment failed: " + e.getMessage());
        }


        Payment payment = new Payment();

        payment.setOrderId(orderId);

        payment.setPaymentId(
                paymentIntent.getId()
        );

        payment.setAmount(amount);

        payment.setPaymentStatus("PENDING");

         paymentRepository.save(payment);
        return new CreatePaymentResponse(
                paymentIntent.getClientSecret()
        );
    }

    public ResponseEntity<Void> handleWebhook(
            String payload,
            HttpServletRequest request
    ) {

        String webhookSecret = stripeConfig.getWebhookSecret();

        String stripeSignature = request.getHeader("Stripe-Signature");

        try {

            Event event = Webhook.constructEvent(
                    payload,
                    stripeSignature,
                    webhookSecret
            );

            System.out.println(
                    "Stripe event API version: " + event.getApiVersion()
            );

            if (event.getType().equals("payment_intent.succeeded")) {

                PaymentIntent paymentIntent =
                        (PaymentIntent) event
                                .getDataObjectDeserializer()
                                .deserializeUnsafe();

                String paymentIntentId =
                        paymentIntent.getId();

                Payment payment =
                        paymentRepository.findByPaymentId(paymentIntentId);

                payment.setPaymentStatus("SUCCEEDED");

                paymentRepository.save(payment);
            }

            return ResponseEntity.ok().build();

        } catch (SignatureVerificationException e) {

            return ResponseEntity.badRequest().build();

        } catch (StripeException e) {

            return ResponseEntity.badRequest().build();
        }

    }

}
