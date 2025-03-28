package dev.backend.webbanthucung.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "order_detail")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_detail_id")
    String order_detail_id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @Column(nullable = false)
    int quantity;

    @Column(nullable = false)
    double price;

    // Constructor đầy đủ
    public OrderDetail(Order order, Product product, int quantity, double price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderDetail() {

    }
}
