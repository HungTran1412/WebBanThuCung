package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.dto.model.PageProductDTO;

public interface ProductService {
    PageProductDTO productsDTO(Integer limit, Integer skip);
}
