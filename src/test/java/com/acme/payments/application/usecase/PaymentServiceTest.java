package com.acme.payments.application.usecase;

import com.acme.payments.application.port.in.AuthorizePaymentCommand;
import com.acme.payments.application.port.out.PaymentRepositoryPort;
import com.acme.payments.domain.model.Payment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentServiceTest {
    private final PaymentRepositoryPort repository = mock(PaymentRepositoryPort.class);
    private final PaymentService service = new PaymentService(repository);

    @Test
    void authorizesPaymentWhenOrderIsNew() {
        var command = new AuthorizePaymentCommand("m1", "o1", "c1", new BigDecimal("100.00"), "pen");
        when(repository.existsByMerchantIdAndOrderId("m1", "o1")).thenReturn(false);
        when(repository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var payment = service.authorize(command);

        assertEquals("PEN", payment.money().currency());
        verify(repository).save(any(Payment.class));
    }

    @Test
    void rejectsDuplicateMerchantOrder() {
        var command = new AuthorizePaymentCommand("m1", "o1", "c1", BigDecimal.TEN, "PEN");
        when(repository.existsByMerchantIdAndOrderId("m1", "o1")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.authorize(command));
        verify(repository, never()).save(any());
    }
}
