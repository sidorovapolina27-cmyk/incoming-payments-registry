package com.example.payments.client.service;

import com.example.payments.client.dto.ClientCreateDto;
import com.example.payments.client.dto.ClientDto;
import com.example.payments.client.entity.Client;
import com.example.payments.client.repository.ClientRepository;
import com.example.payments.error.ConflictException;
import com.example.payments.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public ClientDto create(ClientCreateDto dto) {
        if (clientRepository.existsByInn(dto.getInn())) {
            throw new ConflictException("Клиент с таким ИНН уже существует");
        }
        Client client = new Client();
        client.setFullName(dto.getFullName());
        client.setInn(dto.getInn());
        Client saved = clientRepository.save(client);
        return new ClientDto(saved);
    }

    @Transactional(readOnly = true)
    public ClientDto getById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Клиент не найден"));
        return new ClientDto(client);
    }

    @Transactional(readOnly = true)
    public List<ClientDto> getAll() {
        return clientRepository.findAll().stream()
                .map(ClientDto::new)
                .toList();
    }
}
