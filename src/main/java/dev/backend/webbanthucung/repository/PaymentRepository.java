package dev.backend.webbanthucung.repository;

import dev.backend.webbanthucung.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
