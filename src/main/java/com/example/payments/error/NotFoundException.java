package com.example.payments.error;

/** «Не найдено» — превратится в HTTP 404 (см. ApiExceptionHandler). */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}