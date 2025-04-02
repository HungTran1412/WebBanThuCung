package dev.backend.webbanthucung.controller;

import dev.backend.webbanthucung.dto.request.PaymentRequest;
import dev.backend.webbanthucung.entity.Payment;
import dev.backend.webbanthucung.service.PaymentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> makePayment(@RequestBody PaymentRequest request){
        Payment payment = paymentService.processPayment(request.getOrderId(), request.getPaymentMethod());
        return ResponseEntity.ok(payment);
    }
}
