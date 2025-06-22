package dev.backend.webbanthucung.repository;

import dev.backend.webbanthucung.entities.Order;
import dev.backend.webbanthucung.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    void deleteByOrder(Order order);
}
