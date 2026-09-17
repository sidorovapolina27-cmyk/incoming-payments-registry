package com.example.payments.account.controller;

import com.example.payments.account.dto.AccountCreateDto;
import com.example.payments.account.dto.AccountDto;
import com.example.payments.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/api/clients/{clientId}/accounts") // счёт всегда создаётся ВНУТРИ клиента
    public ResponseEntity<AccountDto> create(@PathVariable Long clientId,
                                             @Valid @RequestBody AccountCreateDto dto) {
        AccountDto created = accountService.createForClient(clientId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/api/accounts/{id}")
    public AccountDto getById(@PathVariable Long id) {
        return accountService.getById(id);
    }

    @GetMapping("/api/clients/{clientId}/accounts")
    public List<AccountDto> findByClient(@PathVariable Long clientId) {
        return accountService.findByClient(clientId);
    }
}
