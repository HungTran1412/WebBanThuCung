package dev.backend.webbanthucung.dto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInOrderDTO {
    Integer id;
    String name;
    Integer age;
    Integer price;
    String quantity;
    String img;
    String description;
    String breed;
    String color;
    String size;
}