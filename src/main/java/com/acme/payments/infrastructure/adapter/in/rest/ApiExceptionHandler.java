package com.acme.payments.infrastructure.adapter.in.rest;
import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.time.OffsetDateTime; import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    ResponseEntity<Map<String,Object>> badRequest(RuntimeException ex){ return error(HttpStatus.BAD_REQUEST, ex); }
    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<Map<String,Object>> notFound(RuntimeException ex){ return error(HttpStatus.NOT_FOUND, ex); }
    private ResponseEntity<Map<String,Object>> error(HttpStatus status, RuntimeException ex){ return ResponseEntity.status(status).body(Map.of("timestamp", OffsetDateTime.now().toString(), "status", status.value(), "error", status.getReasonPhrase(), "message", ex.getMessage())); }
}
