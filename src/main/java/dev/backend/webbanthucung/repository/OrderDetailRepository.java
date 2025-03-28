package dev.backend.webbanthucung.repository;

import dev.backend.webbanthucung.entity.Order;
import dev.backend.webbanthucung.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    void deleteByOrder(Order order);
}
