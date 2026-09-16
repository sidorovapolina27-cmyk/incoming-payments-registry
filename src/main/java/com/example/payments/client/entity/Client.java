package com.example.payments.client.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity                       // «этот класс = таблица»
@Table(name = "client")       // имя таблицы в базе
@Getter
@Setter
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id генерирует сама база (bigint autoincrement)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "inn", nullable = false)
    private String inn;                  // уникальность уже обеспечена в Liquibase (uq_client_inn)

    @CreationTimestamp                   // Hibernate сам поставит дату-время при первой вставке
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
