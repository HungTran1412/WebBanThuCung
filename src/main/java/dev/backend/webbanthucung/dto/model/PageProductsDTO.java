package dev.backend.webbanthucung.dto.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageProductsDTO {
    List<ProductsDTO> product;
    Integer skip;
    Integer limit;
    Integer total;
}
