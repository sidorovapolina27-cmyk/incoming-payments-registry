package com.example.payments.payment.repository;

import com.example.payments.payment.entity.Payment;
import com.example.payments.payment.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    boolean existsByExternalId(String externalId);

    /**
     * Статический запрос. JPQL — это «SQL на языке объектов»:
     * пишем from Payment (имя КЛАССА), а не имя таблицы из Postgres.
     */
    @Query("""
            select p from Payment p
            where p.payeeAccount = :accountNumber
              and p.status = :status
            """)
    List<Payment> findByPayeeAccountAndStatus(@Param("accountNumber") String accountNumber,
                                              @Param("status") PaymentStatus status);
}
