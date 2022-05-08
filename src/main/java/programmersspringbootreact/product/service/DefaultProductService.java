package programmersspringbootreact.product.service;

import org.springframework.stereotype.Service;
import programmersspringbootreact.product.model.Category;
import programmersspringbootreact.product.model.Product;
import programmersspringbootreact.product.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String productName, Category category, long price, String description) {
        return productRepository.insert(new Product(UUID.randomUUID(), productName, category, price, description, LocalDateTime.now(), LocalDateTime.now()));
    }

    @Override
    public void deleteById(UUID uuid) {
        productRepository.deleteById(uuid);
    }
}
