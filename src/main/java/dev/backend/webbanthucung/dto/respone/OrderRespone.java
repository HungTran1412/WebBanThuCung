package dev.backend.webbanthucung.dto.respone;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

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
}
