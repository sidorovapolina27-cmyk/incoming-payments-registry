package com.example.payments;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController // этот класс отвечает на HTTP-запросы
public class PingController {

    @GetMapping("/api/ping") // на какой адрес отвечает метод
    public Map<String, String> ping() {
        return Map.of("status", "ok");
    }
}
