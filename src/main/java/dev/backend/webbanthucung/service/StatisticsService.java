package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.dto.respone.PaymentRespone;

import java.math.BigDecimal;
import java.util.List;

public interface StatisticsService {
    int getOrderQuantity();
    int getContactQuantity();
    int todayOrderQuantity();
    BigDecimal totalAmountToday();
    int thisMonthOrderQuantity();
    BigDecimal totalAmountThisMonth();
    int thisYearOrderQuantity();
    BigDecimal totalAmountThisYear();
    List<PaymentRespone> getAllPayments();
}
