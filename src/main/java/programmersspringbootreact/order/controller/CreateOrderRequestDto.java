package programmersspringbootreact.order.controller;

import programmersspringbootreact.order.model.OrderItem;

import java.util.List;

public record CreateOrderRequestDto(
        String email, String address, String postcode, List<OrderItem> orderItems
) {
}
