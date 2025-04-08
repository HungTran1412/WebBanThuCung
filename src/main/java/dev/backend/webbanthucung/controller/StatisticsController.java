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

import java.util.List;

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

    @GetMapping("/orderquantity")
    public int getOrderQuantity() {
        return statisticsService.getOrderQuantity();
    }
}
