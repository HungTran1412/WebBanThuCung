package dev.backend.webbanthucung.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="product_id")
    Integer id;

    @Column(name ="name", columnDefinition = "NVARCHAR(255)")
    String name;

    @Column(name ="age")
    Integer age;

    @Column(name ="price")
    Integer price;

    @Column(name ="quantity")
    Integer quantity;

    @Column(name="img")
    String img;

    @Column(name ="description",columnDefinition = "NVARCHAR(255)")
    String description;

    @Column(name ="breed",columnDefinition = "NVARCHAR(10)")
    String breed;
}
