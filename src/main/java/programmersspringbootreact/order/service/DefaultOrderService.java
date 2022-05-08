package programmersspringbootreact.order.service;

import org.springframework.stereotype.Service;
import programmersspringbootreact.order.model.Email;
import programmersspringbootreact.order.model.Order;
import programmersspringbootreact.order.model.OrderItem;
import programmersspringbootreact.order.model.OrderStatus;
import programmersspringbootreact.order.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultOrderService implements OrderService {

    private final OrderRepository orderRepository;

    public DefaultOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems) {
        Order order = new Order(UUID.randomUUID(), email, address, postcode, orderItems, OrderStatus.ACCEPTED, LocalDateTime.now(), LocalDateTime.now());
        return orderRepository.insert(order);
    }

    @Override
    public List<Order> getAllOrdersByEmail(String email) {
        return orderRepository.findAllOrdersByEmail(email);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAllOrders();
    }
}
