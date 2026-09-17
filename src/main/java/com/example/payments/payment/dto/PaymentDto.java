package com.example.payments.payment.dto;

import com.example.payments.payment.entity.Payment;
import com.example.payments.payment.entity.PaymentStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class PaymentDto {

    private final Long id;
    private final String externalId;
    private final String payerName;
    private final String payerAccount;
    private final String payeeAccount;
    private final BigDecimal amount;
    private final String currency;
    private final String purpose;
    private final LocalDate valueDate;
    private final PaymentStatus status;
    private final String rejectReason;

    public PaymentDto(Payment payment) {
        this.id = payment.getId();
        this.externalId = payment.getExternalId();
        this.payerName = payment.getPayerName();
        this.payerAccount = payment.getPayerAccount();
        this.payeeAccount = payment.getPayeeAccount();
        this.amount = payment.getAmount();
        this.currency = payment.getCurrency();
        this.purpose = payment.getPurpose();
        this.valueDate = payment.getValueDate();
        this.status = payment.getStatus();
        this.rejectReason = payment.getRejectReason();
    }
}
