package com.tableandtaste.backend.dto;

public class PaymentStatusRequest {

    private String paymentStatus;

    public PaymentStatusRequest(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public PaymentStatusRequest() {

    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
