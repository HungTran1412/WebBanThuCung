package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.entities.Payment;

public interface PaymentService {
    Payment processPayment(String id, String paymentMethod);

}
