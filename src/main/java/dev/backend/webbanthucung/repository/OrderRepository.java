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

    @Query(value = "SELECT COUNT(*) FROM [order] WHERE order_date = CAST(GETDATE() AS DATE)", nativeQuery = true)
    int countOrdersToday();

    @Query(value = "SELECT COUNT(*) FROM [order] WHERE MONTH(order_date) = MONTH(GETDATE()) AND YEAR(order_date) = YEAR(GETDATE())", nativeQuery = true)
    int countOrdersThisMonth();

    @Query(value = "SELECT COUNT(*) FROM [order] WHERE YEAR(order_date) = YEAR(GETDATE())", nativeQuery = true)
    int countOrdersThisYear();
}
