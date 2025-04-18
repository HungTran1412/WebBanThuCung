package dev.backend.webbanthucung.dto.model;

import java.util.List;

public class ProductDetail {
    private ProductsDTO product;
    private List<ProductsDTO> productList;
    public ProductsDTO getProduct() {
        return product;
    }
    public void setProduct(ProductsDTO product) {
        this.product = product;
    }
    public List<ProductsDTO> getProductList() {
        return productList;
    }
    public void setProductList(List<ProductsDTO> productList) {
        this.productList = productList;
    }
}
