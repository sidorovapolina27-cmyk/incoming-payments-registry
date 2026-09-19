package com.example.payments.payment.service;

import com.example.payments.payment.dto.CreatePaymentDto;
import com.example.payments.payment.dto.PaymentDto;
import com.example.payments.payment.xml.IncomingPaymentsXml;
import com.example.payments.payment.xml.PaymentXml;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentImportService {

    private final PaymentService paymentService;

    /** Проводит пачку. Каждый платёж — отдельная транзакция (внутри processPayment):
     *  сбой одного не откатывает остальных. */
    public List<PaymentDto> importPayments(IncomingPaymentsXml incoming) {
        List<PaymentDto> results = new ArrayList<>();

        for (PaymentXml xml : incoming.getPayments()) {
            try {
                CreatePaymentDto dto = toDto(xml);
                results.add(paymentService.processPayment(dto));
            } catch (Exception e) {
                // битый платёж не должен валить всю пачку: фиксируем в лог и идём дальше.
                // (в реальном банке по нему сделали бы отдельную «квитанцию об ошибке»)
                log.warn("Пропущен некорректный платёж externalId={}: {}",
                        xml.getExternalId(), e.getMessage());
            }
        }
        return results;
    }

    private CreatePaymentDto toDto(PaymentXml xml) {
        CreatePaymentDto dto = new CreatePaymentDto();
        dto.setExternalId(xml.getExternalId());
        dto.setPayerName(xml.getPayerName());
        dto.setPayerAccount(xml.getPayerAccount());
        dto.setPayeeAccount(xml.getPayeeAccount());
        dto.setAmount(xml.getAmount());
        dto.setCurrency(xml.getCurrency());
        dto.setPurpose(xml.getPurpose());
        if (xml.getValueDate() != null) {
            dto.setValueDate(LocalDate.parse(xml.getValueDate())); // формат 2026-09-10
        }
        return dto;
    }
}
