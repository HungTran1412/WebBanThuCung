package dev.backend.webbanthucung.dto.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductsDTO {
    private Integer id;
    private String name;
    private Integer age;
    private Integer price;
    private String breed;
    private String color;
    private String size;
    private Integer quantity;
    private String img;
    private String description;
}
