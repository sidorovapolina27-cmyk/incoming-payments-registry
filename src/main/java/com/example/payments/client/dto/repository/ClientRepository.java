package com.example.payments.client.repository;

import com.example.payments.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    // Spring Data сам напишет реализацию по имени метода: exists + By + inn
    boolean existsByInn(String inn);
}
