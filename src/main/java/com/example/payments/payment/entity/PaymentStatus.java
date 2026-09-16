package com.example.payments.payment.entity;

public enum PaymentStatus {
    POSTED,    // деньги зачислены, баланс счёта вырос
    REJECTED   // отказали, баланс не менялся. Причина — в rejectReason
}
