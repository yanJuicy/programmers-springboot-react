package programmersspringbootreact.order.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import programmersspringbootreact.order.model.Email;
import programmersspringbootreact.order.model.Order;
import programmersspringbootreact.order.model.OrderItem;
import programmersspringbootreact.order.model.OrderStatus;
import programmersspringbootreact.product.model.Category;

import java.time.LocalDateTime;
import java.util.*;

import static programmersspringbootreact.utils.JdbcUtils.toLocalDateTime;
import static programmersspringbootreact.utils.JdbcUtils.toUUID;

@Repository
public class OrderJdbcRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Order> findAllOrders() {
        return jdbcTemplate.query("select * from orders", orderRowMapper);
    }

    @Override
    public List<OrderItem> findAllOrderItems() {
        return jdbcTemplate.query("select * from order_items", orderItemRowMapper);
    }

    @Override
    public List<OrderItem> findAllOrderItemsById(UUID orderId) {
        System.out.println("find " + orderId);
        return jdbcTemplate.query("SELECT * FROM order_items WHERE order_id = UUID_TO_BIN(:orderId)",
                Collections.singletonMap("orderId", orderId.toString().getBytes()),
                orderItemRowMapper);
    }

    @Transactional
    @Override
    public Order insert(Order order) {
        jdbcTemplate.update(
                "INSERT INTO orders(order_id, email, address, postcode, order_status, created_at, updated_at) " +
                        "VALUES (UUID_TO_BIN(:orderId), :email, :address, :postcode, :orderStatus, :createdAt, :updatedAt)",
                toOrderParamMap(order));
        order.getOrderItems().forEach(item -> jdbcTemplate.update(
                "INSERT INTO order_items(order_id, product_id, category, price, quantity, created_at, updated_at) " +
                        "VALUES (UUID_TO_BIN(:orderId), UUID_TO_BIN(:productId), :category, :price, :quantity, :createdAt, :updatedAt)",
                toOrderItemParamMap(order.getOrderId(), order.getCreatedAt(), order.getUpdatedAt(), item)));
        return order;
    }

    @Override
    public Optional<Order> findOrderByEmail(Email email) {
        String address = email.getAddress();
        Optional<Order> order = Optional.of(jdbcTemplate.queryForObject(
                "SELECT * FROM orders WHERE email = :email",
                Collections.singletonMap("email", address),
                orderRowMapper));

        return order;
    }

    @Override
    public List<Order> findAllOrdersByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM orders WHERE email = :email", Collections.singletonMap("email", email), orderRowMapper);
    }

    private static final RowMapper<Order> orderRowMapper = (resultSet, i) -> {
        var orderId = toUUID(resultSet.getBytes("order_id"));
        var email = resultSet.getString("email");
        var address = resultSet.getString("address");
        var postcode = resultSet.getString("postcode");
        var orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
        var createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        var updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

        return new Order(orderId, new Email(email), address, postcode, null, orderStatus, createdAt, updatedAt);
    };

    private static final RowMapper<OrderItem> orderItemRowMapper = (resultSet, i) -> {
        var productId = toUUID(resultSet.getBytes("product_id"));
        var category = Category.valueOf(resultSet.getString("category"));
        var price = resultSet.getLong("price");
        var quantity = resultSet.getInt("quantity");

        return new OrderItem(productId, category, price, quantity);
    };

    private Map<String, Object> toOrderParamMap(Order order) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", order.getOrderId().toString().getBytes());
        paramMap.put("email", order.getEmail().getAddress());
        paramMap.put("address", order.getAddress());
        paramMap.put("postcode", order.getPostcode());
        paramMap.put("orderStatus", order.getOrderStatus().toString());
        paramMap.put("createdAt", order.getCreatedAt());
        paramMap.put("updatedAt", order.getUpdatedAt());
        return paramMap;
    }

    private Map<String, Object> toOrderItemParamMap(UUID orderId, LocalDateTime createdAt, LocalDateTime updatedAt, OrderItem item) {
        var paramMap = new HashMap<String, Object>();

        paramMap.put("orderId", orderId.toString().getBytes());
        paramMap.put("productId", item.getProductId().toString().getBytes());
        paramMap.put("category", item.getCategory().toString());
        paramMap.put("price", item.getPrice());
        paramMap.put("quantity", item.getQuantity());
        paramMap.put("createdAt", createdAt);
        paramMap.put("updatedAt", updatedAt);

        return paramMap;
    }
}
