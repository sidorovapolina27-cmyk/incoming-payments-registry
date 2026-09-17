package com.example.payments.account.dto;

import com.example.payments.account.entity.Account;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class AccountDto {

    private final Long id;
    private final String accountNumber;
    private final String currency;
    private final BigDecimal balance;
    private final Long clientId;

    public AccountDto(Account account) {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.currency = account.getCurrency();
        this.balance = account.getBalance();
        this.clientId = account.getClient().getId();
    }
}
