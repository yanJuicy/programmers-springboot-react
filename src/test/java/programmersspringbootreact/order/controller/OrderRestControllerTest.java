package programmersspringbootreact.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import programmersspringbootreact.order.model.OrderItem;
import programmersspringbootreact.order.service.OrderService;
import programmersspringbootreact.product.model.Category;
import programmersspringbootreact.product.model.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderRestController.class)
@ExtendWith(MockitoExtension.class)
class OrderRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    void createOrderTest() throws Exception {
        Product product1 = new Product(UUID.randomUUID(), "test-product1", Category.COFFEE_BEAN_PACKAGE, 1000, "test", LocalDateTime.now(), LocalDateTime.now());
        List<OrderItem> orderItemList = List.of(
                new OrderItem(product1.getProductId(), Category.COFFEE_BEAN_PACKAGE, 2000, 2));

        CreateOrderRequestDto createOrderRequestDto =
                new CreateOrderRequestDto("test@test.com", "test-address", "test-postcode", orderItemList);

        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOrderRequestDto)))
                .andExpect(status().isOk());
    }

}