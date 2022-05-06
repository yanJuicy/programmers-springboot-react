package programmersspringbootreact.order.service;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import programmersspringbootreact.order.model.Email;
import programmersspringbootreact.order.model.Order;
import programmersspringbootreact.order.model.OrderItem;
import programmersspringbootreact.order.repository.OrderJdbcRepository;
import programmersspringbootreact.order.repository.OrderRepository;
import programmersspringbootreact.product.model.Category;
import programmersspringbootreact.product.model.Product;
import programmersspringbootreact.product.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DefaultOrderServiceTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        var config = aMysqldConfig(Version.v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    void createOrderTest() {
        Product product1 = new Product(UUID.randomUUID(), "test-product1", Category.COFFEE_BEAN_PACKAGE, 1000, "test" , LocalDateTime.now(), LocalDateTime.now());
        Product product2 = new Product(UUID.randomUUID(), "test-product2", Category.COFFEE_BEAN_PACKAGE, 2000, "test2" ,LocalDateTime.now(), LocalDateTime.now());
        productRepository.insert(product1);
        productRepository.insert(product2);

        List<OrderItem> orderItemList = List.of(
                new OrderItem(product1.getProductId(), Category.COFFEE_BEAN_PACKAGE, 2000, 2),
                new OrderItem(product2.getProductId(), Category.COFFEE_BEAN_PACKAGE, 6000, 3));

        Order savedOrder = orderService.createOrder(new Email("test@test.com"), "address", "postcode", orderItemList);

        assertThat(savedOrder.getOrderId()).isEqualTo(orderRepository.findOrderByEmail(new Email("test@test.com")).get().getOrderId());
    }

}