package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.entity.Order;
import dev.backend.webbanthucung.entity.Payment;
import dev.backend.webbanthucung.repository.OrderRepository;
import dev.backend.webbanthucung.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    OrderRepository orderRepository;

    public Payment processPayment(String id, String paymentMethod){
        Order order =  orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        if(order.getPayment() != null){
            throw new IllegalArgumentException("Đơn hàng đã được thanh toán!");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentDate(LocalDate.now());
        payment.setTotalAmount(order.getTotalAmount());
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus("Thành công");

        if(payment.getOrder() ==null || payment.getOrder().getOrderId() == null){
            throw new IllegalArgumentException("Mã đơn hàng không thể trống!");
        }

        order.setPayment(payment);

        return paymentRepository.save(payment);
    }
}
