package dev.backend.webbanthucung.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    String fullName;
    String email;
    String phone;
    String address;
    Double totalAmount;
    String note;
    List<OrderDetailRequest> orderDetail;
}
