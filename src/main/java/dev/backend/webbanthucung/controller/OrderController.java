package dev.backend.webbanthucung.controller;

import dev.backend.webbanthucung.dto.request.OrderRequest;
import dev.backend.webbanthucung.dto.request.PendingOrderRequest;
import dev.backend.webbanthucung.dto.respone.OrderRespone;
import dev.backend.webbanthucung.entity.Order;
import dev.backend.webbanthucung.service.Impl.OrderServiceImpl;
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

    //Sua thong tin don hang
    @PatchMapping("/admin/orders/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable String orderId, @RequestBody OrderRequest request) {
        Order updateOrder =  orderService.updateOrder(orderId, request);
        return ResponseEntity.ok(updateOrder);
    }

    //lấy đơn hàng xuất cả list đơn hàng
    @GetMapping("/admin/order-list")
    public  List<OrderRespone> getAllOrdersList() {return orderService.getAllOrdersList();}

    //Lay don hang theo id
    @GetMapping("/admin/order/{orderId}")
    public Order getOrderById(@PathVariable("orderId") String orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/admin/order/{orderId}")
    public Order approveOrder(@PathVariable("orderId") String orderId, @RequestBody PendingOrderRequest request) {
        return orderService.approveOrder(orderId, request);
    }

    @DeleteMapping("/admin/order/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") String orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("true");
    }
}
