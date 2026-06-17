package com.acme.payments.infrastructure.adapter.out.persistence;
import com.acme.payments.application.port.out.PaymentRepositoryPort;
import com.acme.payments.domain.model.Payment;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class PostgresPaymentRepositoryAdapter implements PaymentRepositoryPort {
    private final SpringDataPaymentJpaRepository jpa;
    public PostgresPaymentRepositoryAdapter(SpringDataPaymentJpaRepository jpa) { this.jpa = jpa; }
    public Payment save(Payment payment) { return PaymentMapper.toDomain(jpa.save(PaymentMapper.toEntity(payment))); }
    public Optional<Payment> findById(UUID id) { return jpa.findById(id).map(PaymentMapper::toDomain); }
    public List<Payment> findByMerchantId(String merchantId) { return jpa.findByMerchantId(merchantId).stream().map(PaymentMapper::toDomain).toList(); }
    public boolean existsByMerchantIdAndOrderId(String merchantId, String orderId) { return jpa.existsByMerchantIdAndOrderId(merchantId, orderId); }
}
