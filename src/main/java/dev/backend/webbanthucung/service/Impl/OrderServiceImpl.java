package dev.backend.webbanthucung.service.Impl;

import dev.backend.webbanthucung.dto.model.ProductInOrderDTO;
import dev.backend.webbanthucung.dto.request.OrderRequest;
import dev.backend.webbanthucung.dto.request.PendingOrderRequest;
import dev.backend.webbanthucung.dto.respone.OrderRespone;
import dev.backend.webbanthucung.entities.Order;
import dev.backend.webbanthucung.entities.OrderDetail;
import dev.backend.webbanthucung.entities.Product;
import dev.backend.webbanthucung.entities.Promotion;
import dev.backend.webbanthucung.repository.OrderDetailRepository;
import dev.backend.webbanthucung.repository.OrderRepository;
import dev.backend.webbanthucung.repository.ProductsRepository;
import dev.backend.webbanthucung.repository.PromotionRepository;
import dev.backend.webbanthucung.service.EmailService;
import dev.backend.webbanthucung.service.OrderService;
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
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductsRepository productRepository;

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    EmailService sendEmail;

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

        //Nếu có khuyến mãi
        Promotion promotion = null;
        if (request.getDiscountCode() != null && !request.getDiscountCode().isEmpty()) {
            promotion = promotionRepository.findById(request.getDiscountCode())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy mã khuyến mãi: " + request.getDiscountCode()));
            order.setPromotion(promotion);
            promotion.setOrder(order);
        }

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
            totalPrice += orderDetail.getPrice() * orderDetail.getQuantity();
        }

        //log.info("Khuyen mai: {}", promotion.getDiscountCode());

        //Áp dụng khuyến mãi nếu có
        BigDecimal totalAmount = BigDecimal.valueOf(totalPrice);
        if (promotion != null && "AVAIABLE".equals(promotion.getStatus())) {
            BigDecimal discountRate = new BigDecimal(0.1);
            promotion.setStatus("UNAVAIABLE");
            totalAmount = totalAmount.multiply(BigDecimal.ONE.subtract(discountRate));
        }

        //Gán danh sách OrderDetail vào Order
        order.setOrderDetails(orderDetails);
        order.setTotalAmount(totalAmount.setScale(2, RoundingMode.HALF_UP));

        //Duyệt danh sách
        StringBuilder productsList = new StringBuilder();
        for (OrderDetail detail : orderDetails) {
            productsList.append("\n- ")
                    .append(detail.getProduct().getName())
                    .append(" (Số lượng: ")
                    .append(detail.getQuantity())
                    .append(", Đơn giá: ")
                    .append(detail.getPrice())
                    .append(")");
        }

        String subject = "Đặt hàng thành công!";
        String body = "Quý khách vừa đặt đơn hàng:\n" +
                "Mã đơn hàng: " + order.getOrderId()
                + "\nHọ tên: " + order.getFullName()
                + "\nSố điện thoại: " + order.getPhone()
                + "\nEmail: " + order.getEmail()
                + "\nĐịa chỉ: " + order.getAddress()
                + "\nTổng tiền: " + totalAmount.setScale(2, RoundingMode.HALF_UP) + " VND"
                + "\nSản phẩm đã đặt:" + productsList.toString()
                + "\n\nCảm ơn quý khách đã mua hàng tại cửa hàng chúng tôi!";

        sendEmail.sendMail(order.getEmail(), subject, body);

        return orderRepository.save(order);
    }


    //Ham huy don hang
    @Transactional
    public void cancelOrder(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) throw new RuntimeException("Không tìm thấy ID đơn hàng: " + orderId);

        Order order1 = order.get();

        String subject = "Hủy đơn hàng thành công!";
        String body = "Quý khách đã hủy thành công đơn hàng:" +
                "\nMã đơn hàng: " + order1.getOrderId()
                + "\n\nHân hạnh được phục vụ quý khách vào thời gian tới!";

        sendEmail.sendMail(order1.getEmail(), subject, body);

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

    //hàm sửa đơn hàng
    @Transactional
    public Order updateOrder(String id, OrderRequest request) {
        Order order = getOrderById(id);

        // Cập nhật thông tin đơn hàng
        order.setFullName(request.getFullName());
        order.setEmail(request.getEmail());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setNote(request.getNote());

        // Nếu người dùng có gửi orderDetail mới lên
        if (request.getOrderDetail() != null && !request.getOrderDetail().isEmpty()) {
            // Xóa orderDetail cũ
            orderDetailRepository.deleteByOrder(order);

            // Tạo orderDetail mới
            List<OrderDetail> orderDetails = request.getOrderDetail().stream().map(detail -> {
                Product product = productRepository.findById(detail.getProductId())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + detail.getProductId()));
                return new OrderDetail(order, product, 1, product.getPrice());
            }).collect(Collectors.toList());

            order.setOrderDetails(orderDetails);

            // Cập nhật lại tổng tiền
            double totalPrice = orderDetails.stream()
                    .mapToDouble(od -> od.getPrice() * od.getQuantity())
                    .sum();
            order.setTotalAmount(BigDecimal.valueOf(totalPrice).setScale(2, RoundingMode.HALF_UP));
        }

        return orderRepository.save(order);
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

    public List<OrderRespone> getAllOrdersList() {
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
