package com.example.payments.account.service;

import com.example.payments.account.dto.AccountCreateDto;
import com.example.payments.account.dto.AccountDto;
import com.example.payments.account.entity.Account;
import com.example.payments.account.repository.AccountRepository;
import com.example.payments.client.entity.Client;
import com.example.payments.client.repository.ClientRepository;
import com.example.payments.error.ConflictException;
import com.example.payments.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public AccountDto createForClient(Long clientId, AccountCreateDto dto) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Клиент не найден"));

        if (accountRepository.existsByAccountNumber(dto.getAccountNumber())) {
            throw new ConflictException("Счёт с таким номером уже существует");
        }

        Account account = new Account();
        account.setAccountNumber(dto.getAccountNumber());
        account.setCurrency(dto.getCurrency());
        account.setBalance(BigDecimal.ZERO); // новый счёт пустой
        account.setClient(client);
        Account saved = accountRepository.save(account);
        return new AccountDto(saved);
    }

    @Transactional(readOnly = true)
    public AccountDto getById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Счёт не найден"));
        return new AccountDto(account);
    }

    @Transactional(readOnly = true)
    public List<AccountDto> findByClient(Long clientId) {
        return accountRepository.findByClientId(clientId).stream()
                .map(AccountDto::new)
                .toList();
    }
}
