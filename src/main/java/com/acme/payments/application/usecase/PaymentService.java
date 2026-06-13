package com.acme.payments.application.usecase;

import com.acme.payments.application.port.in.*;
import com.acme.payments.application.port.out.PaymentRepositoryPort;
import com.acme.payments.domain.model.Payment;
import com.acme.payments.domain.valueobject.Money;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
public class PaymentService implements PaymentUseCase {
    private final PaymentRepositoryPort repository;
    public PaymentService(PaymentRepositoryPort repository) { this.repository = repository; }
    public Payment authorize(AuthorizePaymentCommand command) {
        if (repository.existsByMerchantIdAndOrderId(command.merchantId(), command.orderId()))
            throw new IllegalArgumentException("Payment already exists for merchant/order");
        return repository.save(Payment.authorize(command.merchantId(), command.orderId(), command.customerId(), new Money(command.amount(), command.currency())));
    }
    public Payment capture(UUID paymentId) { var p = get(paymentId); p.capture(); return repository.save(p); }
    public Payment reject(UUID paymentId, String reason) { var p = get(paymentId); p.reject(reason); return repository.save(p); }
    @Transactional(readOnly = true) public Optional<Payment> findById(UUID paymentId) { return repository.findById(paymentId); }
    @Transactional(readOnly = true) public List<Payment> findByMerchant(String merchantId) { return repository.findByMerchantId(merchantId); }
    private Payment get(UUID id) { return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Payment not found")); }
}
