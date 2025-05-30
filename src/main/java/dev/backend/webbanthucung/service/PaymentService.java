package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.entity.Payment;

public interface PaymentService {
    Payment processPayment(String id, String paymentMethod);

}
