package dev.backend.webbanthucung.repository;

import dev.backend.webbanthucung.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    @Query(value = "SELECT TOP (1) o.order_id FROM [order] o WHERE o.order_id LIKE ('ORD' + ? + '%') ORDER BY o.order_id DESC",
            nativeQuery = true)
    String findLastOrderId(@Param("datePart") String datePart);

    Order findByOrderId(String orderId);
}
