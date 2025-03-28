package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.dto.request.OrderRequest;
import dev.backend.webbanthucung.dto.request.PendingOrderRequest;
import dev.backend.webbanthucung.dto.respone.OrderRespone;
import dev.backend.webbanthucung.entity.Order;
import dev.backend.webbanthucung.entity.OrderDetail;
import dev.backend.webbanthucung.entity.Product;
import dev.backend.webbanthucung.repository.OrderDetailRepository;
import dev.backend.webbanthucung.repository.OrderRepository;
import dev.backend.webbanthucung.repository.ProductRepository;
import dev.backend.webbanthucung.repository.PromotionRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    public String generateOrderId() {
        //lay ngay hien tai
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));

        //lay order id lon nhat cua ngay hien tai
        String latestOrderId = orderRepository.findLastOrderId(datePart);

        int nextNumber = 1;
        if (latestOrderId != null) {
            // Tách số thứ tự từ order_id cuối cùng
            String lastNumberStr = latestOrderId.substring(latestOrderId.lastIndexOf('-') + 1);
            nextNumber = Integer.parseInt(lastNumberStr) + 1;
        }

        //Tao id moi
        return String.format("ORD%s-%03d", datePart, nextNumber);
    }


    //Ham tao don hang
    public Order createOrder(OrderRequest request) {
        //Tạo Order mới
        Order order = new Order();

        order.setOrderId(generateOrderId());
        order.setFullName(request.getFullName());
        order.setEmail(request.getEmail());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setTotalAmount(request.getTotalAmount());
        order.setOrderDate(LocalDate.now());
        order.setStatus("PENDING");

        //Tạo danh sách OrderDetail
        List<OrderDetail> orderDetails = request.getOrderDetail().stream().map(detail -> {
            Product product = productRepository.findById(detail.getProductId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + detail.getProductId()));

            // Quan trọng: Gán order vào OrderDetail!
            return new OrderDetail(order, product, 1, product.getPrice());
        }).collect(Collectors.toList());

        //Gán danh sách OrderDetail vào Order
        order.setOrderDetails(orderDetails);


        return orderRepository.save(order);
    }


    //Ham xem chi tiet hoa don


    //Ham lay don hang theo id
    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy ID đơn hàng: " + orderId));
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

    //Ham xu ly don hang
    public Order approveOrder(String id, PendingOrderRequest request) {
        Order order = getOrderById(id);

        order.setStatus(request.getStatus());

        return orderRepository.save(order);
    }
}
