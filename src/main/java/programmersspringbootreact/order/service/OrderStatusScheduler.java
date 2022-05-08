package programmersspringbootreact.order.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import programmersspringbootreact.order.model.Order;
import programmersspringbootreact.order.model.OrderStatus;
import programmersspringbootreact.order.repository.OrderRepository;

import java.util.List;

@Component
public class OrderStatusScheduler {

    private final OrderRepository orderRepository;

    public OrderStatusScheduler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Scheduled(cron = "0 0 14 * * *")
    public void changeOrderStatus() {
        List<Order> orderList = orderRepository.findAllOrders();

        for (Order order : orderList) {
            if (order.getOrderStatus() == OrderStatus.ACCEPTED)
                order.setOrderStatus(OrderStatus.READY_FOR_DELIVERY);
            else if (order.getOrderStatus() == OrderStatus.READY_FOR_DELIVERY)
                order.setOrderStatus(OrderStatus.SHIPPED);

            orderRepository.update(order);
        }
    }

}
