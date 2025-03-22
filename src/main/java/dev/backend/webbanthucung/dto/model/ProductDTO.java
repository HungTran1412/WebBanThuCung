package dev.backend.webbanthucung.dto.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {
    Integer id;
    String name;
    Integer age;
    Integer price;
    Integer quantity;
    String img;
    String des;
    String gender;
}
