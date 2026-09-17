package com.example.payments.payment.repository;

import com.example.payments.payment.entity.Payment;
import com.example.payments.payment.entity.PaymentStatus;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Сборщик условий для динамического поиска платежей. */
public final class PaymentSpecifications {

    private PaymentSpecifications() {
        // утилитный класс: создавать его не нужно
    }

    /** Точный статус. null = условие не добавляем. */
    public static Specification<Payment> hasStatus(PaymentStatus status) {
        return (root, query, cb) ->
                status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }

    /** valueDate >= from. */
    public static Specification<Payment> valueDateFrom(LocalDate from) {
        return (root, query, cb) ->
                from == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("valueDate"), from);
    }

    /** valueDate <= to. */
    public static Specification<Payment> valueDateTo(LocalDate to) {
        return (root, query, cb) ->
                to == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("valueDate"), to);
    }

    /** amount >= min. */
    public static Specification<Payment> amountFrom(BigDecimal min) {
        return (root, query, cb) ->
                min == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("amount"), min);
    }

    /** amount <= max. */
    public static Specification<Payment> amountTo(BigDecimal max) {
        return (root, query, cb) ->
                max == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("amount"), max);
    }

    /** Вхождение текста в назначение платежа, без учёта регистра. */
    public static Specification<Payment> purposeContains(String text) {
        return (root, query, cb) -> {
            if (text == null || text.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("purpose")), "%" + text.toLowerCase() + "%");
        };
    }

    /** Точное совпадение счёта получателя («как прислали»). */
    public static Specification<Payment> accountNumberEquals(String accountNumber) {
        return (root, query, cb) -> {
            if (accountNumber == null || accountNumber.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("payeeAccount"), accountNumber);
        };
    }

    /** Вхождение имени в ФИО владельца счёта получателя.
     *  Путь: payment -> payeeAccountRef (счёт) -> client (клиент) -> fullName.
     *  Это и есть JOIN: три таблицы в одном условии. */
    public static Specification<Payment> clientNameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) {
                return cb.conjunction();
            }
            var payeeAccount = root.join("payeeAccountRef");
            var client = payeeAccount.join("client");
            return cb.like(cb.lower(client.get("fullName")), "%" + name.toLowerCase() + "%");
        };
    }
}
