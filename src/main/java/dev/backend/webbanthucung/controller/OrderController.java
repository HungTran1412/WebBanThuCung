package dev.backend.webbanthucung.controller;

import dev.backend.webbanthucung.dto.request.OrderRequest;
import dev.backend.webbanthucung.dto.respone.OrderRespone;
import dev.backend.webbanthucung.entity.Order;
import dev.backend.webbanthucung.service.OrderService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //Tao don hang
    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        Order newOrder = orderService.createOrder(request);
        return ResponseEntity.ok(newOrder);
    }

    //Lay tat ca don hang
    @GetMapping("/admin/order")
    public List<OrderRespone> getAllOrders() {
        return orderService.getAllOrders();
    }
}
