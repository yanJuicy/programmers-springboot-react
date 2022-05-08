package programmersspringbootreact.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import programmersspringbootreact.order.service.OrderService;

import java.util.UUID;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String ordersPage(Model model) {
        var orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order-list";
    }

    @GetMapping("/orders/{orderId}")
    public String orderDetailPage(@PathVariable UUID orderId, Model model) {
        var orderItems = orderService.getAllOrderItemsById(orderId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("orderItems", orderItems);
        return "order-detail";
    }

}
