package programmersspringbootreact.order.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import programmersspringbootreact.order.model.Email;
import programmersspringbootreact.order.model.Order;
import programmersspringbootreact.order.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/v1/orders")
    public Order createOrder(@RequestBody CreateOrderRequestDto orderRequest) {
        return orderService.createOrder(
                new Email(orderRequest.email()),
                orderRequest.address(),
                orderRequest.postcode(),
                orderRequest.orderItems()
        );
    }

    @GetMapping("/api/v1/orders/email")
    public List<Order> orderDetailPage(@RequestParam String email) {
        email.replace("%40", "@");
        var orders = orderService.getAllOrdersByEmail(email);
        return orders;
    }

}
