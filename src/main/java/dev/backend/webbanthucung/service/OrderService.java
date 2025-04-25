package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.dto.model.ProductInOrderDTO;
import dev.backend.webbanthucung.dto.request.OrderRequest;
import dev.backend.webbanthucung.dto.request.PendingOrderRequest;
import dev.backend.webbanthucung.dto.respone.OrderRespone;
import dev.backend.webbanthucung.entity.Order;
import dev.backend.webbanthucung.entity.OrderDetail;
import dev.backend.webbanthucung.entity.Product;
import dev.backend.webbanthucung.repository.OrderDetailRepository;
import dev.backend.webbanthucung.repository.OrderRepository;
import dev.backend.webbanthucung.repository.ProductsRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    ProductsRepository productRepository;

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
        order.setNote(request.getNote());
        order.setOrderDate(LocalDate.now());
        order.setStatus("PENDING");

        //Tạo danh sách OrderDetail
        List<OrderDetail> orderDetails = request.getOrderDetail().stream().map(detail -> {
            Product product = productRepository.findById(detail.getProductId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + detail.getProductId()));

            //Gán order vào OrderDetail!
            return new OrderDetail(order, product, 1, product.getPrice());
        }).collect(Collectors.toList());


        //tính tổng tiền
        double totalPrice = 0;
        for (OrderDetail orderDetail : orderDetails) {
            totalPrice += orderDetail.getPrice();
        }

        //Gán danh sách OrderDetail vào Order
        order.setOrderDetails(orderDetails);
        order.setTotalAmount(BigDecimal.valueOf(totalPrice).setScale(2, RoundingMode.HALF_UP));

        return orderRepository.save(order);
    }


    //Ham huy don hang
    @Transactional
    public void cancelOrder(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) throw new RuntimeException("Không tìm thấy ID đơn hàng: " + orderId);

        Order order1 = order.get();

        orderDetailRepository.deleteByOrder(order1);
//        order1.setStatus("CANCELLED");
//        orderRepository.save(order1);
        orderRepository.delete(order1);
    }


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
                order.getNote(),
                order.getTotalAmount(),
                order.getStatus()
        )).collect(Collectors.toList());
    }

    public List<OrderRespone> getAllOrdersList(){
        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(order -> {
            List<ProductInOrderDTO> products = order.getOrderDetails().stream()
                    .map(orderDetail -> {
                        Product p = orderDetail.getProduct();
                        return new ProductInOrderDTO(
                                p.getId(),
                                p.getName(),
                                p.getAge(),
                                p.getPrice(),
                                p.getQuantity() == null ? null : String.valueOf(p.getQuantity()),
                                p.getImg(),
                                p.getDescription(),
                                p.getBreed(),
                                p.getColor(),
                                p.getSize()
                        );
                    })
                    .collect(Collectors.toList());

            return new OrderRespone(
                    order.getOrderId(),
                    order.getOrderDate(),
                    order.getFullName(),
                    order.getEmail(),
                    order.getPhone(),
                    order.getAddress(),
                    order.getNote(),
                    order.getTotalAmount(),
                    order.getStatus(),
                    products
            );
        }).collect(Collectors.toList());
    }

    //Ham xu ly don hang
    public Order approveOrder(String id, PendingOrderRequest request) {
        Order order = getOrderById(id);

        order.setStatus(request.getStatus());

        return orderRepository.save(order);
    }


}
