package com.example.payments.client.controller;

import com.example.payments.client.dto.ClientCreateDto;
import com.example.payments.client.dto.ClientDto;
import com.example.payments.client.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/api/clients")
    public ResponseEntity<ClientDto> create(@Valid @RequestBody ClientCreateDto dto) {
        ClientDto created = clientService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created); // 201
    }

    @GetMapping("/api/clients/{id}")
    public ClientDto getById(@PathVariable Long id) {
        return clientService.getById(id); // не найден -> NotFoundException -> 404
    }

    @GetMapping("/api/clients")
    public List<ClientDto> getAll() {
        return clientService.getAll();
    }
}
