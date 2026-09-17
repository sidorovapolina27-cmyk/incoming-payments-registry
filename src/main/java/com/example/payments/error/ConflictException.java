package com.example.payments.error;

/** «Конфликт с существующими данными» — превратится в HTTP 409. */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}