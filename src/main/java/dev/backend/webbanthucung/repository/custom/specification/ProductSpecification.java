package dev.backend.webbanthucung.repository.custom.specification;

import dev.backend.webbanthucung.entities.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> isFilter(String a) {
        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (a != null && !a.equals("")) {
                String[] result = a.split(",");

                if (result.length > 0 &&result[0] != null && !result[0].equals("")) {
                    predicates.add(builder.equal(root.get("breed"), result[0]));
                }
                if (result.length > 1 &&result[1] != null && !result[1].equals("")) {
                    predicates.add(builder.equal(root.get("color"), result[1]));
                }
                if (result.length > 2&&result[2] != null && !result[2].equals("")) {
                    predicates.add(builder.equal(root.get("size"), result[2]));
                }

            }
            // builder.and(predicates.toArray(new Predicate[0]));
            if (predicates.isEmpty()) {
                return builder.conjunction();
            } else {
                return builder.and(predicates.toArray(new Predicate[0]));
            }

        };
    }

    public static Specification<Product> RelatedProducts(String breed, String color, String size, Integer id) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.equal(root.get("breed"), breed));

            predicates.add(builder.equal(root.get("color"), color));

            predicates.add(builder.equal(root.get("size"), size));

            predicates.add(builder.notEqual(root.get("id"), id));
            if (predicates.isEmpty()) {
                return builder.conjunction();
            } else {
                return builder.and(predicates.toArray(new Predicate[0]));
            }

        };
    }


    public static Specification<Product> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }
}
