package dev.backend.webbanthucung.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "promotion")
public class Promotion {

    @Column(name = "email")
    String email;

    @Id
    @Column(name = "discount_code")
    String discountCode;

    @Column(name = "status")
    String status;

    @OneToOne(mappedBy = "promotion")
    Order order;
}
