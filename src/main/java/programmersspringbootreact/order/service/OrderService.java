package programmersspringbootreact.order.service;

import programmersspringbootreact.order.model.Email;
import programmersspringbootreact.order.model.Order;
import programmersspringbootreact.order.model.OrderItem;

import java.util.List;

public interface OrderService {

    Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);

}
