package programmersspringbootreact.order.repository;

import programmersspringbootreact.order.model.Order;
import programmersspringbootreact.order.model.OrderItem;

import java.util.List;

public interface OrderRepository {
    List<Order> findAllOrders();
    List<OrderItem> findAllOrderItems();
    Order insert(Order order);
}
