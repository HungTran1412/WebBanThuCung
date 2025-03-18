package dev.backend.webbanthucung.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "order_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_detail_id")
    String orderDetailId;

    @Column(name = "order_id")
    String orderId;

    @Column(name = "product_id")
    String product_id;

    @Column(name = "quantity")
    int quantity;

    @Column(name = "price")
    double price;

    public OrderDetail(String orderId, String product_id, int quantity, double price) {
        this.orderId = orderId;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }
}
