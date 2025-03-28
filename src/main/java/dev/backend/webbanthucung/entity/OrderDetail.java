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

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    Order orderId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product_id;

    @Column(name = "quantity")
    int quantity;

    @Column(name = "price")
    double price;

    public OrderDetail(Order orderId, Product product_id, double price) {
        this.product_id = product_id;
        this.orderId = orderId;
        this.price = price;
    }
}
