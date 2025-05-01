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
    @Id
    @Column(name = "email")
    String email;

    @Column(name = "discount_code")
    String discountCode;

    @Column(name = "status")
    String status;
}
