package com.example.payments.config;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor // Lombok: конструктор с final-полями, Spring подставит их сам
public class SettingsController {

    private final PaymentProperties paymentProperties;

    @GetMapping("/api/settings/max-amount")
    public Map<String, Object> maxAmount() {
        return Map.of("maxAmount", paymentProperties.getMaxAmount());
    }
}
