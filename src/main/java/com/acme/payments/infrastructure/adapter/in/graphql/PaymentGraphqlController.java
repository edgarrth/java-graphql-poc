package com.acme.payments.infrastructure.adapter.in.graphql;
import com.acme.payments.application.port.in.*;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import java.util.*;

@Controller
public class PaymentGraphqlController {
    private final PaymentUseCase useCase;
    public PaymentGraphqlController(PaymentUseCase useCase) { this.useCase = useCase; }
    @QueryMapping public PaymentDto payment(@Argument UUID id) { return useCase.findById(id).map(PaymentDto::from).orElse(null); }
    @QueryMapping public List<PaymentDto> paymentsByMerchant(@Argument String merchantId) { return useCase.findByMerchant(merchantId).stream().map(PaymentDto::from).toList(); }
    @MutationMapping public PaymentDto authorizePayment(@Argument AuthorizePaymentInput input) { return PaymentDto.from(useCase.authorize(new AuthorizePaymentCommand(input.merchantId(), input.orderId(), input.customerId(), input.amount(), input.currency()))); }
    @MutationMapping public PaymentDto capturePayment(@Argument UUID id) { return PaymentDto.from(useCase.capture(id)); }
    @MutationMapping public PaymentDto rejectPayment(@Argument UUID id, @Argument String reason) { return PaymentDto.from(useCase.reject(id, reason)); }
}
