package com.acme.payments.application.port.in;
import com.acme.payments.domain.model.Payment;
import java.util.*;
public interface PaymentUseCase {
    Payment authorize(AuthorizePaymentCommand command);
    Payment capture(UUID paymentId);
    Payment reject(UUID paymentId, String reason);
    Optional<Payment> findById(UUID paymentId);
    List<Payment> findByMerchant(String merchantId);
}
