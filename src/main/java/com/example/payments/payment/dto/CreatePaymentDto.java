package com.example.payments.payment.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CreatePaymentDto {

    @NotBlank(message = "externalId обязателен")
    private String externalId;

    private String payerName;

    private String payerAccount;

    @NotBlank(message = "Счёт получателя обязателен")
    @Pattern(regexp = "\\d{20}", message = "Счёт получателя должен состоять из 20 цифр")
    private String payeeAccount;

    @NotNull(message = "Сумма обязательна")
    @Positive(message = "Сумма должна быть больше 0")
    @Digits(integer = 19, fraction = 2, message = "Не больше 2 знаков после запятой")
    private BigDecimal amount;

    @NotBlank(message = "Валюта обязательна")
    @Pattern(regexp = "RUB", message = "В этом проекте валюта только RUB")
    private String currency;

    private String purpose;

    private LocalDate valueDate; // формат 2026-09-10 — Jackson понимает его из коробки
}
