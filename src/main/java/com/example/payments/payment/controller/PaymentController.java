package com.example.payments.payment.controller;

import com.example.payments.payment.dto.CreatePaymentDto;
import com.example.payments.payment.dto.PaymentDto;
import com.example.payments.payment.entity.PaymentStatus;
import com.example.payments.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/api/payments")
    public ResponseEntity<PaymentDto> create(@Valid @RequestBody CreatePaymentDto dto) {
        PaymentDto payment = paymentService.processPayment(dto);
        // ВАЖНО: и POSTED, и REJECTED — это 201 Created (платёж принят и зафиксирован)
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    @GetMapping("/api/payments/{id}")
    public PaymentDto getById(@PathVariable Long id) {
        return paymentService.getById(id);
    }

    /** Статический запрос: счёт + статус. */
    @GetMapping("/api/payments/by-account")
    public List<PaymentDto> byAccount(@RequestParam String accountNumber,
                                      @RequestParam PaymentStatus status) {
        return paymentService.findByAccountAndStatus(accountNumber, status);
    }
}
