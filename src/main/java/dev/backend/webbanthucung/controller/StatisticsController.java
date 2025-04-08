package dev.backend.webbanthucung.controller;

import dev.backend.webbanthucung.dto.respone.PaymentRespone;
import dev.backend.webbanthucung.service.StatisticsService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin")
@RestController
public class StatisticsController {
    @Autowired
    StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }


    @GetMapping("/payments")
    public List<PaymentRespone> getAllPayment(){
        return statisticsService.getAllPayments();
    }

    @GetMapping("/statistics/orderquantity")
    public int getOrderQuantity() {
        return statisticsService.getOrderQuantity();
    }

    @GetMapping("/statistics/today")
    public Map<String, Integer> getTodayOrderQuantity() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Số đơn", statisticsService.todayOrderQuantity());
        return map;
    }

    @GetMapping("/statistics/month")
    public Map<String, Integer> getMonthOrderQuantity() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Số đơn", statisticsService.thisMonthOrderQuantity());
        return map;
    }

    @GetMapping("/statistics/year")
    public Map<String, Integer> getYearOrderQuantity() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Số đơn", statisticsService.thisYearOrderQuantity());
        return map;
    }
}
