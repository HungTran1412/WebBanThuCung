package dev.backend.webbanthucung.dto.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductsDTO {
    Integer id;
    String name;
    Integer age;
    Integer price;
    String breed;
    String color;
    String size;
    Integer quantity;
    String img;
    String description;
}
