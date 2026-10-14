package com.tableandtaste.backend.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

   private final String secretKey;
   private final String webhookSecret;

    public StripeConfig(@Value("${stripe.secret-key}") String secretKey,
                        @Value("${stripe.webhook-secret}") String webhookSecret) {
        this.secretKey = secretKey;
        this.webhookSecret = webhookSecret;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getWebhookSecret() {
        return webhookSecret;
    }

    @PostConstruct
    public void automatedMethod() {
        Stripe.apiKey = secretKey;
    }


}
