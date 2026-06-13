package com.acme.payments.application.port.out;
import com.acme.payments.domain.model.Payment;
import java.util.*;
public interface PaymentRepositoryPort {
    Payment save(Payment payment);
    Optional<Payment> findById(UUID id);
    List<Payment> findByMerchantId(String merchantId);
    boolean existsByMerchantIdAndOrderId(String merchantId, String orderId);
}
