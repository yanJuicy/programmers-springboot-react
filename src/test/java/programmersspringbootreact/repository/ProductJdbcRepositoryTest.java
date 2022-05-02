package programmersspringbootreact.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import programmersspringbootreact.model.Category;
import programmersspringbootreact.model.Product;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class ProductJdbcRepositoryTest {

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

    @BeforeEach
    void cleanEach() {
        repository.deleteAll();
    }


    @Autowired
    ProductRepository repository;

    @Test
    @DisplayName("상품 추가")
    void testInsert() {
        Product product = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L, "description", LocalDateTime.now(), LocalDateTime.now());
        repository.insert(product);
        var all = repository.findAll();
        assertThat(all.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("상품 전체 조회")
    void testFindAll() {
        Product product = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L, "description", LocalDateTime.now(), LocalDateTime.now());
        repository.insert(product);
        Product product2 = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L, "description", LocalDateTime.now(), LocalDateTime.now());
        repository.insert(product2);
        Product product3 = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L, "description", LocalDateTime.now(), LocalDateTime.now());
        repository.insert(product3);

        var all = repository.findAll();
        assertThat(all.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("상품을 id로 조회")
    void testFindById() {
        UUID uuid = UUID.randomUUID();
        Product product = new Product(uuid, "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L, "description", LocalDateTime.now(), LocalDateTime.now());
        repository.insert(product);
        var searchProduct = repository.findById(uuid).get();

        assertThat(searchProduct.getProductId()).isEqualTo(product.getProductId());
    }

    @Test
    @DisplayName("상품을 상품명으로 조회")
    void testFindByName() {
        Product product = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L, "description", LocalDateTime.now(), LocalDateTime.now());
        repository.insert(product);
        var searchProduct = repository.findByName("new-product").get();

        assertThat(searchProduct.getProductName()).isEqualTo(product.getProductName());
    }

    @Test
    @DisplayName("상품을 전체 삭제")
    void testDeleteAll() {
        Product product = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L, "description", LocalDateTime.now(), LocalDateTime.now());
        repository.insert(product);
        Product product2 = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L, "description", LocalDateTime.now(), LocalDateTime.now());
        repository.insert(product2);
        Product product3 = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L, "description", LocalDateTime.now(), LocalDateTime.now());
        repository.insert(product3);

        repository.deleteAll();
        var all = repository.findAll();
        assertThat(all.isEmpty()).isTrue();
    }


}