package com.acme.payments.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    @Test
    void normalizesCurrencyToUpperCase() {
        var money = new Money(new BigDecimal("10.50"), "pen");
        assertEquals("PEN", money.currency());
    }

    @Test
    void rejectsNonPositiveAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Money(BigDecimal.ZERO, "PEN"));
    }

    @Test
    void rejectsInvalidCurrency() {
        assertThrows(IllegalArgumentException.class, () -> new Money(BigDecimal.TEN, "XYZ"));
    }
}
