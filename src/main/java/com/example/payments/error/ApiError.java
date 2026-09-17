package com.example.payments.error;

import java.util.List;

/** Единая форма ошибки для всего API (record — компактный класс
 *  для неизменяемых данных, Java 17). */
public record ApiError(
        int status,
        String error,
        String message,
        String path,
        List<FieldErrorDto> fieldErrors
) {
    public record FieldErrorDto(String field, String message) {
    }
}
