package com.acme.payments.infrastructure.adapter.in.graphql;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AuthorizePaymentInput(
        @NotBlank @Size(max = 60) String merchantId,
        @NotBlank @Size(max = 80) String orderId,
        @NotBlank @Size(max = 80) String customerId,
        @NotNull @Positive BigDecimal amount,
        @NotBlank @Size(min = 3, max = 3) String currency) {
}
