package com.acme.payments.infrastructure.adapter.in.graphql;
import com.acme.payments.domain.model.Payment;
import com.acme.payments.domain.model.PaymentStatus;
import java.math.BigDecimal; import java.time.OffsetDateTime; import java.util.UUID;
public record PaymentDto(UUID id, String merchantId, String orderId, String customerId, BigDecimal amount, String currency, PaymentStatus status, String failureReason, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
    public static PaymentDto from(Payment p) { return new PaymentDto(p.id(), p.merchantId(), p.orderId(), p.customerId(), p.money().amount(), p.money().currency(), p.status(), p.failureReason(), p.createdAt(), p.updatedAt()); }
}
