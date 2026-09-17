package com.example.payments.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientCreateDto {

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 200, message = "Имя должно быть от 2 до 200 символов")
    private String fullName;

    @NotBlank(message = "ИНН обязателен")
    @Pattern(regexp = "\\d{10}|\\d{12}", message = "ИНН должен содержать 10 или 12 цифр")
    private String inn;
}
