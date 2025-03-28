package dev.backend.webbanthucung.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

    @Column(name = "total_amount")
    Double totalAmount;

    @Column(name = "status")
    String status;
}
