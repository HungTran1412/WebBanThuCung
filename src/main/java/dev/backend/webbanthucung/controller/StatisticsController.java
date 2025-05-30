package dev.backend.webbanthucung.controller;

import dev.backend.webbanthucung.dto.respone.PaymentRespone;
import dev.backend.webbanthucung.service.Impl.StatisticsServiceImpl;
import dev.backend.webbanthucung.service.StatisticsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RequestMapping("/admin")
@RestController
public class StatisticsController {
    @Autowired
    StatisticsService statisticsService;

    @GetMapping("/payments")
    public List<PaymentRespone> getAllPayment(){
        return statisticsService.getAllPayments();
    }

    @GetMapping("/contacts/quantity")
    public Map<String, Integer> getQuantity(){
        Map<String, Integer>  map = new HashMap<>();
        map.put("quantity", statisticsService.getContactQuantity());
        return map;
    }

    @GetMapping("/order/orderquantity")
    public int getOrderQuantity() {
        return statisticsService.getOrderQuantity();
    }

    @GetMapping("/order/today")
    public Map<String, Object> getTodayOrderQuantity() {
        Map<String, Object> map = new HashMap<>();
        map.put("Số đơn", statisticsService.todayOrderQuantity());
        map.put("Tổng tiền", statisticsService.totalAmountToday());
        return map;
    }

    @GetMapping("/order/month")
    public Map<String, Object> getMonthOrderQuantity() {
        Map<String, Object> map = new HashMap<>();
        map.put("Số đơn", statisticsService.thisMonthOrderQuantity());
        map.put("Tổng tiền", statisticsService.totalAmountThisMonth());
        return map;
    }

    @GetMapping("/order/year")
    public Map<String, Object> getYearOrderQuantity() {
        Map<String, Object> map = new HashMap<>();
        map.put("orders", statisticsService.thisYearOrderQuantity());
        map.put("total", statisticsService.totalAmountThisYear());
        return map;
    }
}
