package com.acme.payments.infrastructure.adapter.out.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface SpringDataPaymentJpaRepository extends JpaRepository<PaymentEntity, UUID> {
    List<PaymentEntity> findByMerchantId(String merchantId);
    boolean existsByMerchantIdAndOrderId(String merchantId, String orderId);
}
