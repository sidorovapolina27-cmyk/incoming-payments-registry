package com.example.payments.payment.entity;

import com.example.payments.account.entity.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false)
    private String externalId;           // номер платежа во внешней системе, уникален

    @Column(name = "payer_name")
    private String payerName;            // плательщик — не наш клиент, просто строка

    @Column(name = "payer_account")
    private String payerAccount;

    @Column(name = "payee_account", nullable = false)
    private String payeeAccount;         // номер счёта получателя «как прислали»

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "purpose")
    private String purpose;              // назначение платежа

    @Column(name = "value_date")
    private LocalDate valueDate;         // дата валютирования

    @Enumerated(EnumType.STRING)         // хранить как строку POSTED/REJECTED, а не как число!
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Column(name = "reject_reason")
    private String rejectReason;         // заполняется только при REJECTED

    /** Ссылка на счёт получателя, если он нашёлся в базе. Пустая ссылка = счёт не наш
     *  (платёж будет REJECTED). Обрати внимание: это ДРУГОЕ поле, чем payeeAccount-строка. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payee_account_id")
    private Account payeeAccountRef;
}
