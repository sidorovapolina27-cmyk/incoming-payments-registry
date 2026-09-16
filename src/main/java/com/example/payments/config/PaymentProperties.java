package com.example.payments.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "payments") // сюда попадает блок payments: из настроек
@Getter
@Setter
public class PaymentProperties {

    /** Максимальная сумма платежа. Значение по умолчанию — запасной вариант,
     *  если в Consul ключа нет или Consul недоступен. */
    private BigDecimal maxAmount = new BigDecimal("100000");
}
