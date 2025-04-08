package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.dto.respone.PaymentRespone;
import dev.backend.webbanthucung.entity.Order;
import dev.backend.webbanthucung.entity.Payment;
import dev.backend.webbanthucung.repository.OrderRepository;
import dev.backend.webbanthucung.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class StatisticsService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PaymentRepository paymentRepository;

    //Ham dem so luong don hang
    public int getOrderQuantity() {
        int quantity = 0;

        for(Order order : orderRepository.findAll()) {
            quantity ++;
        }

        return quantity;
    }

    //ham lay tat ca don hang da thanh toan
    public List<PaymentRespone> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();

        return payments.stream().map(payment -> new PaymentRespone(
                Integer.parseInt(payment.getId().toString()),
                payment.getOrder().getOrderId(),
                payment.getPaymentDate(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getTotalAmount(),
                payment.getTransactionId()
        )).collect(Collectors.toList());
    }
}
