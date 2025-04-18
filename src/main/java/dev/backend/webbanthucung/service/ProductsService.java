package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.dto.model.PageProductsDTO;
import dev.backend.webbanthucung.dto.model.ProductDetail;
import dev.backend.webbanthucung.entity.Product;

public interface ProductsService {
    PageProductsDTO productSearch(String name, Integer skip, Integer limit, String filter);

    ProductDetail Detail(Integer id);

    Product saveProduct(Product product);

    Product updateProduct(Integer id, Product productDetails);

    void deleteProduct(Integer id);

    PageProductsDTO findProductsByRangeWithSpecification(Integer skip, Integer limit, String filter);
}
