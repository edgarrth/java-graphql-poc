package com.acme.payments.domain.model;

import com.acme.payments.domain.valueobject.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    @Test
    void authorizesAndCapturesPayment() {
        var payment = Payment.authorize("m1", "o1", "c1", new Money(new BigDecimal("15.00"), "PEN"));
        assertEquals(PaymentStatus.AUTHORIZED, payment.status());
        payment.capture();
        assertEquals(PaymentStatus.CAPTURED, payment.status());
    }

    @Test
    void capturedPaymentCannotBeRejected() {
        var payment = Payment.authorize("m1", "o1", "c1", new Money(BigDecimal.TEN, "PEN"));
        payment.capture();
        assertThrows(IllegalStateException.class, () -> payment.reject("declined"));
    }

    @Test
    void rejectRequiresReason() {
        var payment = Payment.authorize("m1", "o1", "c1", new Money(BigDecimal.TEN, "PEN"));
        assertThrows(IllegalArgumentException.class, () -> payment.reject(" "));
    }
}
