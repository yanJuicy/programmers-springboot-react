package programmersspringbootreact.product.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.distribution.Version;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import programmersspringbootreact.product.model.Category;
import programmersspringbootreact.product.model.Product;
import programmersspringbootreact.product.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

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
    @DisplayName("μν μΆκ°")
    void testInsert() {
        Product product = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L, "description", LocalDateTime.now(), LocalDateTime.now());
        repository.insert(product);
        var all = repository.findAll();
        assertThat(all.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("μν μ μ²΄ μ‘°ν")
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
    @DisplayName("μνμ idλ‘ μ‘°ν")
    void testFindById() {
        UUID uuid = UUID.randomUUID();
        Product product = new Product(uuid, "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L, "description", LocalDateTime.now(), LocalDateTime.now());
        repository.insert(product);
        var searchProduct = repository.findById(uuid).get();

        assertThat(searchProduct.getProductId()).isEqualTo(product.getProductId());
    }

    @Test
    @DisplayName("μνμ μνλͺμΌλ‘ μ‘°ν")
    void testFindByName() {
        Product product = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L, "description", LocalDateTime.now(), LocalDateTime.now());
        repository.insert(product);
        var searchProduct = repository.findByName("new-product").get();

        assertThat(searchProduct.getProductName()).isEqualTo(product.getProductName());
    }

    @Test
    @DisplayName("μνμ μΉ΄νκ³ λ¦¬λ‘ μ‘°ν")
    void testFindByCategory() {
        Product product = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L, "description", LocalDateTime.now(), LocalDateTime.now());
        repository.insert(product);
        Product product2 = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L, "description", LocalDateTime.now(), LocalDateTime.now());
        repository.insert(product2);
        Product product3 = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L, "description", LocalDateTime.now(), LocalDateTime.now());
        repository.insert(product3);

        var productList = repository.findByCategory(Category.COFFEE_BEAN_PACKAGE);
        MatcherAssert.assertThat(productList.isEmpty(), is(false));
    }

    @Test
    @DisplayName("μνμ μμ ")
    void testUpdate() {
        Product product = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L, "description", LocalDateTime.now(), LocalDateTime.now());
        repository.insert(product);
        product.setProductName("update-product");
        repository.update(product);

        var updatedProduct = repository.findById(product.getProductId());
        MatcherAssert.assertThat(updatedProduct.isEmpty(), is(false));
        MatcherAssert.assertThat(updatedProduct.get(), samePropertyValuesAs(product));
    }

    @Test
    @DisplayName("μνμ μ μ²΄ μ­μ ")
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