package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.dto.request.OrderRequest;
import dev.backend.webbanthucung.dto.respone.OrderRespone;
import dev.backend.webbanthucung.entity.Order;
import dev.backend.webbanthucung.entity.OrderDetail;
import dev.backend.webbanthucung.repository.OrderDetailRepository;
import dev.backend.webbanthucung.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    public String generateOrderId() {
        //lay ngay hien tai
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));

        //lay order id lon nhat cua ngay hien tai
        String latestOrderId = orderRepository.findLastOrderId(datePart);

        int nextNumber = 1;
        if(latestOrderId != null){
            // Tách số thứ tự từ order_id cuối cùng
            String lastNumberStr = latestOrderId.substring(latestOrderId.lastIndexOf('-') + 1);
            nextNumber = Integer.parseInt(lastNumberStr) + 1;
        }

        //Tao id moi
        return String.format("ORD%s-%03d", datePart, nextNumber);
    }

    //Ham tao don hang
    public Order createOrder(OrderRequest request) {
        String newOrderId = generateOrderId();

        Order order = new Order();

        // Thiet lap du lieu
        order.setOrderId(newOrderId);
        order.setOrderDate(LocalDate.now());
        order.setFullName(request.getFullName());
        order.setEmail(request.getEmail());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setTotalAmount(request.getTotalAmount());
        order.setStatus("PENDING");

        // Luu vao database
        orderRepository.save(order);

        // Kiểm tra orderDetail có null không
        if (request.getOrderDetail() != null && !request.getOrderDetail().isEmpty()) {
            // Tạo danh sách OrderDetail từ danh sách sản phẩm đặt hàng
            List<OrderDetail> orderDetails = request.getOrderDetail().stream()
                    .map(detail -> new OrderDetail(newOrderId, detail.getProductId(),
                            detail.getQuantity(), detail.getPrice()))
                    .collect(Collectors.toList());

            // Lưu tất cả chi tiết đơn hàng vào database
            orderDetailRepository.saveAll(orderDetails);
        } else {
            log.warn("OrderDetail is null or empty for orderId: {}", newOrderId);
        }

        return order;
    }

    //Ham lay don hang theo id
    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new RuntimeException("Không tìm thấy ID đơn hàng cho orderId: " + orderId));
    }

    //Ham lay tat ca don hang
    public List<OrderRespone> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(order -> new OrderRespone(
                order.getOrderId(),
                order.getOrderDate(),
                order.getFullName(),
                order.getEmail(),
                order.getPhone(),
                order.getAddress(),
                order.getTotalAmount(),
                order.getStatus()
                )).collect(Collectors.toList());
    }
}
