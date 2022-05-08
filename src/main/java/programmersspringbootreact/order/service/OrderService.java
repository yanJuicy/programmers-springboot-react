package programmersspringbootreact.order.service;

import programmersspringbootreact.order.model.Email;
import programmersspringbootreact.order.model.Order;
import programmersspringbootreact.order.model.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);

    List<Order> getAllOrdersByEmail(String email);

    List<Order> getAllOrders();

    List<OrderItem> getAllOrderItemsById(UUID orderId);

    List<OrderItem> getAllOrderItems();
}
