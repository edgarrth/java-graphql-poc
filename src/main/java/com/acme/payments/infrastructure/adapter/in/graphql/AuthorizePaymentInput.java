package com.acme.payments.infrastructure.adapter.in.graphql;
import java.math.BigDecimal;
public record AuthorizePaymentInput(String merchantId, String orderId, String customerId, BigDecimal amount, String currency) {}
