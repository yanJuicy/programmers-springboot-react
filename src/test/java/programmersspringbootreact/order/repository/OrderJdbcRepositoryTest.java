package programmersspringbootreact.order.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import programmersspringbootreact.order.model.Email;
import programmersspringbootreact.order.model.OrderItem;
import programmersspringbootreact.order.model.OrderStatus;
import programmersspringbootreact.order.model.Order;
import programmersspringbootreact.product.model.Category;
import programmersspringbootreact.product.model.Product;
import programmersspringbootreact.product.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
class OrderJdbcRepositoryTest {

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
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    void findAllTest() {
        Product product1 = new Product(UUID.randomUUID(), "test-product1", Category.COFFEE_BEAN_PACKAGE, 1000, "test" ,LocalDateTime.now(), LocalDateTime.now());
        Product product2 = new Product(UUID.randomUUID(), "test-product2", Category.COFFEE_BEAN_PACKAGE, 2000, "test2" ,LocalDateTime.now(), LocalDateTime.now());
        productRepository.insert(product1);
        productRepository.insert(product2);

        List<OrderItem> orderItemList = List.of(
                new OrderItem(product1.getProductId(), Category.COFFEE_BEAN_PACKAGE, 2000, 2),
                new OrderItem(product2.getProductId(), Category.COFFEE_BEAN_PACKAGE, 6000, 3));
        Order order = new Order(UUID.randomUUID(), new Email("test@test.com"), "test city", "test code", orderItemList, OrderStatus.ACCEPTED, LocalDateTime.now(), LocalDateTime.now());
        orderRepository.insert(order);

        List<Order> orders = orderRepository.findAllOrders();
        List<OrderItem> orderItems = orderRepository.findAllOrderItems();

        assertEquals(orders.size(), 1);
        assertEquals(orderItems.size(), 2);
    }

    @Test
    void insertTest() {
        Product product1 = new Product(UUID.randomUUID(), "test-product1", Category.COFFEE_BEAN_PACKAGE, 1000, "test" ,LocalDateTime.now(), LocalDateTime.now());
        Product product2 = new Product(UUID.randomUUID(), "test-product2", Category.COFFEE_BEAN_PACKAGE, 2000, "test2" ,LocalDateTime.now(), LocalDateTime.now());
        productRepository.insert(product1);
        productRepository.insert(product2);

        List<OrderItem> orderItemList = List.of(
                new OrderItem(product1.getProductId(), Category.COFFEE_BEAN_PACKAGE, 2000, 2),
                new OrderItem(product2.getProductId(), Category.COFFEE_BEAN_PACKAGE, 6000, 3));
        Order order = new Order(UUID.randomUUID(), new Email("test@test.com"), "test city", "test code", orderItemList, OrderStatus.ACCEPTED, LocalDateTime.now(), LocalDateTime.now());
        orderRepository.insert(order);

        List<Order> orders = orderRepository.findAllOrders();
        List<OrderItem> orderItems = orderRepository.findAllOrderItems();

        assertThat(orders).isNotEmpty();
        assertThat(orderItems).isNotEmpty();
    }


}