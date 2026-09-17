package com.example.payments.client.dto;

import com.example.payments.client.entity.Client;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ClientDto {

    private final Long id;
    private final String fullName;
    private final String inn;
    private final LocalDateTime createdAt;

    /** Конструктор-маппер: entity -> DTO. Поля плоские, без ссылок на другие entity. */
    public ClientDto(Client client) {
        this.id = client.getId();
        this.fullName = client.getFullName();
        this.inn = client.getInn();
        this.createdAt = client.getCreatedAt();
    }
}
