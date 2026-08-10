package com.acme.payments.infrastructure.adapter.in.rest;
import com.acme.payments.application.port.in.*;
import com.acme.payments.infrastructure.adapter.in.graphql.*;
import jakarta.validation.Valid; import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal; import java.util.*;

@RestController @RequestMapping("/payments/v1/payments")
public class PaymentRestController {
    private final PaymentUseCase useCase;
    public PaymentRestController(PaymentUseCase useCase) { this.useCase = useCase; }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public PaymentDto authorize(@Valid @RequestBody AuthorizePaymentRequest request) {
        return PaymentDto.from(useCase.authorize(new AuthorizePaymentCommand(request.merchantId(), request.orderId(), request.customerId(), request.amount(), request.currency())));
    }
    @PostMapping("/{paymentId}/capture") public PaymentDto capture(@PathVariable UUID paymentId) { return PaymentDto.from(useCase.capture(paymentId)); }
    @PostMapping("/{paymentId}/reject") public PaymentDto reject(@PathVariable UUID paymentId, @Valid @RequestBody RejectPaymentRequest request) { return PaymentDto.from(useCase.reject(paymentId, request.reason())); }
    @GetMapping("/{paymentId}") public PaymentDto get(@PathVariable UUID paymentId) { return useCase.findById(paymentId).map(PaymentDto::from).orElseThrow(); }
    public record AuthorizePaymentRequest(@NotBlank @Size(max=60) String merchantId, @NotBlank @Size(max=80) String orderId, @NotBlank @Size(max=80) String customerId, @NotNull @Positive BigDecimal amount, @NotBlank @Size(min=3,max=3) String currency) {}
    public record RejectPaymentRequest(@NotBlank @Size(max=250) String reason) {}
}
