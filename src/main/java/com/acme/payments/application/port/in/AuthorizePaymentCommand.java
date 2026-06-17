package com.acme.payments.application.port.in;
import java.math.BigDecimal;
public record AuthorizePaymentCommand(String merchantId, String orderId, String customerId, BigDecimal amount, String currency) {}
