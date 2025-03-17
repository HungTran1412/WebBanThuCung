package dev.backend.webbanthucung.repository;

import dev.backend.webbanthucung.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    //List<ProductsEntity> getProduct(Integer limit, Integer skip);
    List<Product> findByIdBetween(Integer limit, Integer skip);
    List<Product> findByIdGreaterThanEqual(Integer skip);
    List<Product> findByIdLessThanEqual(Integer id);
}
