package dev.backend.webbanthucung.controller;

import dev.backend.webbanthucung.dto.request.OrderRequest;
import dev.backend.webbanthucung.dto.request.PendingOrderRequest;
import dev.backend.webbanthucung.dto.respone.OrderRespone;
import dev.backend.webbanthucung.entity.Order;
import dev.backend.webbanthucung.entity.OrderDetail;
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
    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }


//    @GetMapping("/orderDetail")
//    public OrderDetail getOrderDetail(@RequestParam String orderId) {}

    //Lay tat ca don hang
    @GetMapping("/admin/order")
    public List<OrderRespone> getAllOrders() {
        return orderService.getAllOrders();
    }

    //Lay don hang theo id
    @GetMapping("/admin/order/{orderId}")
    public Order getOrderById(@PathVariable("orderId") String orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/admin/order/{orderId}")
    public Order approveOrder(@PathVariable("orderId") String orderId, @RequestBody PendingOrderRequest request) {
        return orderService.approveOrder(orderId, request);
    }
    
}
