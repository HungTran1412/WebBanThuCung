package dev.backend.webbanthucung.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    Integer id;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false)
    Order order;

    @Column(name = "payment_date", nullable = false)
    LocalDate paymentDate;

    @Column(name = "total_amount", nullable = false)
    BigDecimal totalAmount;

    @Column(name = "payment_method", nullable = false)
    String paymentMethod;

    @Column(name = "payment_status", nullable = false)
    String paymentStatus;

    @Column(name = "transaction_id", unique = true)
    String transactionId;
}
