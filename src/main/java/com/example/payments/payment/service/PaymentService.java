package com.example.payments.payment.service;

import com.example.payments.account.entity.Account;
import com.example.payments.account.repository.AccountRepository;
import com.example.payments.config.PaymentProperties;
import com.example.payments.error.ConflictException;
import com.example.payments.error.NotFoundException;
import com.example.payments.payment.dto.CreatePaymentDto;
import com.example.payments.payment.dto.PaymentDto;
import com.example.payments.payment.entity.Payment;
import com.example.payments.payment.entity.PaymentStatus;
import com.example.payments.payment.repository.PaymentRepository;
import com.example.payments.payment.repository.PaymentSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final PaymentProperties paymentProperties; // лимит из Consul (день 2)

    /**
     * Проводит платёж по трём правилам. REJECTED — это НЕ ошибка HTTP:
     * платёж сохраняется со статусом REJECTED, наружу уходит обычный 201.
     */
    @Transactional
    public PaymentDto processPayment(CreatePaymentDto dto) {
        // Повтор externalId — ошибка HTTP: второй раз один платёж не проводим
        if (paymentRepository.existsByExternalId(dto.getExternalId())) {
            throw new ConflictException("Платёж с таким externalId уже существует");
        }

        Payment payment = new Payment();
        payment.setExternalId(dto.getExternalId());
        payment.setPayerName(dto.getPayerName());
        payment.setPayerAccount(dto.getPayerAccount());
        payment.setPayeeAccount(dto.getPayeeAccount());
        payment.setAmount(dto.getAmount());
        payment.setCurrency(dto.getCurrency());
        payment.setPurpose(dto.getPurpose());
        payment.setValueDate(dto.getValueDate());

        // Правило 1: счёт получателя у нас есть?
        Account payeeAccount = accountRepository.findByAccountNumber(dto.getPayeeAccount())
                .orElse(null);

        if (payeeAccount == null) {
            // REJECTED: счёта нет, баланс НИЧЕЙ не меняется
            payment.setStatus(PaymentStatus.REJECTED);
            payment.setRejectReason("Счёт получателя не найден");
        } else if (dto.getAmount().compareTo(paymentProperties.getMaxAmount()) > 0) {
            // Правило 2: сумма больше лимита из Consul — REJECTED, баланс не трогаем
            payment.setStatus(PaymentStatus.REJECTED);
            payment.setRejectReason("Сумма превышает лимит");
        } else {
            // Правило 3: всё ок — зачисляем
            payment.setStatus(PaymentStatus.POSTED);
            payment.setRejectReason(null);
            payeeAccount.setBalance(payeeAccount.getBalance().add(dto.getAmount()));
            accountRepository.save(payeeAccount);
        }

        // Ссылку на счёт сохраняем в любом случае (null, если счёт не наш)
        payment.setPayeeAccountRef(payeeAccount);

        Payment saved = paymentRepository.save(payment);
        return new PaymentDto(saved);
    }

    @Transactional(readOnly = true)
    public PaymentDto getById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Платёж не найден"));
        return new PaymentDto(payment);
    }

    @Transactional(readOnly = true)
    public List<PaymentDto> search(PaymentStatus status,
                                   LocalDate dateFrom,
                                   LocalDate dateTo,
                                   BigDecimal minAmount,
                                   BigDecimal maxAmount,
                                   String clientName,
                                   String purpose,
                                   String accountNumber) {
        Specification<Payment> spec = Specification.allOf(
                PaymentSpecifications.hasStatus(status),
                PaymentSpecifications.valueDateFrom(dateFrom),
                PaymentSpecifications.valueDateTo(dateTo),
                PaymentSpecifications.amountFrom(minAmount),
                PaymentSpecifications.amountTo(maxAmount),
                PaymentSpecifications.clientNameContains(clientName),
                PaymentSpecifications.purposeContains(purpose),
                PaymentSpecifications.accountNumberEquals(accountNumber)
        );
        return paymentRepository.findAll(spec).stream()
                .map(PaymentDto::new)
                .toList();
    }

    public List<PaymentDto> findByAccountAndStatus(String accountNumber, PaymentStatus status) {
        return List.of();
    }}
