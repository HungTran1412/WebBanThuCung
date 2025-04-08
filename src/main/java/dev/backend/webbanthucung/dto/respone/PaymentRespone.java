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
public class PaymentRespone {
    int paymentId;
    String orderId;
    LocalDate paymentDate;
    String paymentMethod;
    String paymentStatus;
    BigDecimal totalAmount;
    String transactionId;
}
