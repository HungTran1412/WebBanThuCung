package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.dto.request.OrderRequest;
import dev.backend.webbanthucung.dto.request.PendingOrderRequest;
import dev.backend.webbanthucung.dto.respone.OrderRespone;
import dev.backend.webbanthucung.entities.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequest request);
    void cancelOrder(String orderId);
    Order getOrderById(String orderId);
    Order updateOrder(String id, OrderRequest request);
    List<OrderRespone> getAllOrders();
    List<OrderRespone> getAllOrdersList();
    Order approveOrder(String id, PendingOrderRequest request);
}
