package com.example.payments.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreateDto {

    @NotBlank(message = "Номер счёта обязателен")
    @Pattern(regexp = "\\d{20}", message = "Номер счёта должен состоять ровно из 20 цифр")
    private String accountNumber;

    @NotBlank(message = "Валюта обязательна")
    @Pattern(regexp = "RUB", message = "В этом проекте валюта только RUB")
    private String currency;
}
