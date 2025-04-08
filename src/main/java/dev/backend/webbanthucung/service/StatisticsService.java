package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.dto.respone.PaymentRespone;
import dev.backend.webbanthucung.entity.Contact;
import dev.backend.webbanthucung.entity.Order;
import dev.backend.webbanthucung.entity.Payment;
import dev.backend.webbanthucung.repository.ContactRepository;
import dev.backend.webbanthucung.repository.OrderRepository;
import dev.backend.webbanthucung.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Autowired
    ContactRepository contactRepository;

    //Ham dem so luong don hang
    public int getOrderQuantity() {
        int quantity = 0;

        for(Order order : orderRepository.findAll()) {
            quantity ++;
        }

        return quantity;
    }

    //ham dem so luong lien he
    public int getContactQuantity(){
        int quantity = 0;

        for(Contact contact : contactRepository.findAll()) quantity++;

        return quantity;
    }

    //Ham lay so luong don hang trong ngay
    public int todayOrderQuantity() {
        return orderRepository.countOrdersToday();
    }

    public BigDecimal totalAmountToday() {
        return orderRepository.totalAmountToday();
    }

    //ham dem so don hang trong thang
    public int thisMonthOrderQuantity() {
        return orderRepository.countOrdersThisMonth();
    }

    public BigDecimal totalAmountThisMonth() {
        return orderRepository.totalAmountThisMonth();
    }

    //ham dem so luong don han trong nam
    public int thisYearOrderQuantity() {
        return orderRepository.countOrdersThisYear();
    }

    public BigDecimal totalAmountThisYear() {
        return orderRepository.totalAmountThisYear();
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
