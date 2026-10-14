package com.tableandtaste.backend.dto;

public class CreatePaymentResponse {

    private final String clientSecret;

    public CreatePaymentResponse(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
