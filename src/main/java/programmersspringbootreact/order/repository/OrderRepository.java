package programmersspringbootreact.order.repository;

import programmersspringbootreact.order.model.Email;
import programmersspringbootreact.order.model.Order;
import programmersspringbootreact.order.model.OrderItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    List<Order> findAllOrders();
    List<OrderItem> findAllOrderItems();
    List<OrderItem> findAllOrderItemsById(UUID orderId);
    Order insert(Order order);
    Optional<Order> findOrderByEmail(Email email);
    List<Order> findAllOrdersByEmail(String email);
}
