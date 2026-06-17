package com.acme.payments.infrastructure.adapter.out.persistence;
import com.acme.payments.domain.model.Payment;
import com.acme.payments.domain.valueobject.Money;
public final class PaymentMapper {
    private PaymentMapper() {}
    public static Payment toDomain(PaymentEntity e) {
        return Payment.restore(e.id, e.merchantId, e.orderId, e.customerId, new Money(e.amount, e.currency), e.status, e.failureReason, e.createdAt, e.updatedAt);
    }
    public static PaymentEntity toEntity(Payment p) {
        var e = new PaymentEntity(); e.id=p.id(); e.merchantId=p.merchantId(); e.orderId=p.orderId(); e.customerId=p.customerId();
        e.amount=p.money().amount(); e.currency=p.money().currency(); e.status=p.status(); e.failureReason=p.failureReason(); e.createdAt=p.createdAt(); e.updatedAt=p.updatedAt(); return e;
    }
}
