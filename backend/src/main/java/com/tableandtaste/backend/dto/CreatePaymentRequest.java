package com.tableandtaste.backend.dto;

public class CreatePaymentRequest {

    private Long orderId;

    public CreatePaymentRequest(Long orderId) {
        this.orderId = orderId;
    }
    public CreatePaymentRequest() {

    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
