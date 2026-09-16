package com.example.payments.account.entity;

import com.example.payments.client.entity.Client;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;        // строка, а НЕ число: 20 цифр и ведущие нули

    @Column(name = "currency", nullable = false)
    private String currency;             // в этом проекте всегда RUB

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;          // деньги — только BigDecimal, никогда double!

    /** Счёт принадлежит клиенту: у одного клиента может быть много счетов. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
