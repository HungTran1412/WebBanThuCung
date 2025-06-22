package dev.backend.webbanthucung.repository;

import dev.backend.webbanthucung.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product,Integer>, JpaSpecificationExecutor<Product> {


    List<Product> findBynameContaining(String name);
    List<Product> findBynameContainingAndIdNot(String name,Integer id);
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
    List<Product> findByIdBetween(Integer limit, Integer skip);
    List<Product> findByIdGreaterThanEqual(Integer skip);
    List<Product> findByIdLessThanEqual(Integer id);

}
