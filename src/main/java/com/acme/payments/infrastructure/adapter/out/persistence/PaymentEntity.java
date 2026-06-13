package com.acme.payments.infrastructure.adapter.out.persistence;

import com.acme.payments.domain.model.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity @Table(name = "payments", uniqueConstraints = @UniqueConstraint(columnNames = {"merchant_id", "order_id"}))
public class PaymentEntity {
    @Id public UUID id;
    @Column(name="merchant_id", nullable=false) public String merchantId;
    @Column(name="order_id", nullable=false) public String orderId;
    @Column(name="customer_id", nullable=false) public String customerId;
    @Column(nullable=false, precision=19, scale=2) public BigDecimal amount;
    @Column(nullable=false, length=3) public String currency;
    @Enumerated(EnumType.STRING) @Column(nullable=false) public PaymentStatus status;
    @Column(name="failure_reason") public String failureReason;
    @Column(name="created_at", nullable=false) public OffsetDateTime createdAt;
    @Column(name="updated_at", nullable=false) public OffsetDateTime updatedAt;
}
