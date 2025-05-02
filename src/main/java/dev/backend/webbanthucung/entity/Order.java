package dev.backend.webbanthucung.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "[order]")
public class Order {
    @Id
    @Column(name = "order_id")
    String orderId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @Column(name = "order_date")
    LocalDate orderDate;

    @Column(name = "full_name")
    String fullName;

    @Column(name = "email")
    String email;

    @Column(name = "phone")
    String phone;

    @Column(name = "address")
    String address;

    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "total_amount")
    BigDecimal totalAmount;

    @Column(name = "status")
    String status;

    @Column(name = "note")
    String note;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    Payment payment;

    @OneToOne
    @JoinColumn(name = "discount_code", referencedColumnName = "discount_code")
    Promotion promotion;
}
