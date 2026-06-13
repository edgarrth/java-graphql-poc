package com.acme.payments.domain.valueobject;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(BigDecimal amount, String currency) {
    public Money {
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("amount must be positive");
        Currency.getInstance(currency);
        currency = currency.toUpperCase();
    }
}
