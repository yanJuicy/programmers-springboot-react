package programmersspringbootreact.service;

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
import programmersspringbootreact.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class DefaultProductServiceTest {

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

    @AfterEach
    void dataCleanup() {
        productRepository.deleteAll();
    }

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("카테고리별 상품 조회")
    void getProductsByCategoryTest() {
        productRepository.insert(new Product(UUID.randomUUID(), "test", Category.COFFEE_BEAN_PACKAGE, 1000, "test", LocalDateTime.now(), LocalDateTime.now()));

        List<Product> productListByService = productService.getProductsByCategory(Category.COFFEE_BEAN_PACKAGE);

        List<Product> productListByRepository = productRepository.findByCategory(Category.COFFEE_BEAN_PACKAGE);

        assertEquals(productListByService.size(), productListByRepository.size());
    }

    @Test
    @DisplayName("상품 생성")
    void createProductTest() {
        Product savedProduct = productService.createProduct("test", Category.COFFEE_BEAN_PACKAGE, 1000, "test");

        Optional<Product> findProduct = productRepository.findById(savedProduct.getProductId());

        assertThat(findProduct).isPresent();
        assertEquals(findProduct.get().getProductId(), savedProduct.getProductId());
    }

    @Test
    @DisplayName("상품 전체 조회 테스트")
    void getAllProductsTest() {
        Product savedProduct = productService.createProduct("test", Category.COFFEE_BEAN_PACKAGE, 1000, "test");

        List<Product> allProducts = productService.getAllProducts();

        assertAll(
                () -> assertThat(allProducts).hasSize(1),
                () -> assertThat(allProducts.get(0).getProductId()).isEqualTo(savedProduct.getProductId()),
                () -> assertThat(allProducts.get(0).getProductName()).isEqualTo(savedProduct.getProductName()),
                () -> assertThat(allProducts.get(0).getCategory()).isEqualTo(savedProduct.getCategory()),
                () -> assertThat(allProducts.get(0).getPrice()).isEqualTo(savedProduct.getPrice())
        );
    }

}