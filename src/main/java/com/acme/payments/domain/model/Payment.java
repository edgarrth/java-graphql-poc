package com.acme.payments.domain.model;

import com.acme.payments.domain.valueobject.Money;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Payment {
    private final UUID id;
    private final String merchantId;
    private final String orderId;
    private final String customerId;
    private final Money money;
    private PaymentStatus status;
    private String failureReason;
    private final OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    private Payment(UUID id, String merchantId, String orderId, String customerId, Money money,
                    PaymentStatus status, String failureReason, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id; this.merchantId = merchantId; this.orderId = orderId; this.customerId = customerId;
        this.money = money; this.status = status; this.failureReason = failureReason;
        this.createdAt = createdAt; this.updatedAt = updatedAt;
    }
    public static Payment authorize(String merchantId, String orderId, String customerId, Money money) {
        var now = OffsetDateTime.now();
        return new Payment(UUID.randomUUID(), merchantId, orderId, customerId, money, PaymentStatus.AUTHORIZED, null, now, now);
    }
    public static Payment restore(UUID id, String merchantId, String orderId, String customerId, Money money,
                                  PaymentStatus status, String failureReason, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        return new Payment(id, merchantId, orderId, customerId, money, status, failureReason, createdAt, updatedAt);
    }
    public void capture() {
        if (status != PaymentStatus.AUTHORIZED) throw new IllegalStateException("Only AUTHORIZED payments can be captured");
        status = PaymentStatus.CAPTURED; updatedAt = OffsetDateTime.now();
    }
    public void reject(String reason) {
        if (status == PaymentStatus.CAPTURED) throw new IllegalStateException("Captured payments cannot be rejected");
        status = PaymentStatus.REJECTED; failureReason = reason; updatedAt = OffsetDateTime.now();
    }
    public UUID id(){return id;} public String merchantId(){return merchantId;} public String orderId(){return orderId;}
    public String customerId(){return customerId;} public Money money(){return money;} public PaymentStatus status(){return status;}
    public String failureReason(){return failureReason;} public OffsetDateTime createdAt(){return createdAt;} public OffsetDateTime updatedAt(){return updatedAt;}
}
