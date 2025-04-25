package dev.backend.webbanthucung.dto.respone;

import dev.backend.webbanthucung.dto.model.ProductInOrderDTO;
import dev.backend.webbanthucung.entity.OrderDetail;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRespone {
    String orderId;
    LocalDate orderDate;
    String fullName;
    String email;
    String phone;
    String address;
    String note;
    BigDecimal totalAmount;
    String status;
    List<ProductInOrderDTO> products;

    public OrderRespone(String orderId, LocalDate orderDate, String fullName, String email, String phone, String address, String note, BigDecimal totalAmount, String status) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.note = note;
        this.totalAmount = totalAmount;
        this.status = status;
    }
}
