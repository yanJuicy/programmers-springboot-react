package programmersspringbootreact.product.repository;

import programmersspringbootreact.product.model.Category;
import programmersspringbootreact.product.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    List<Product> findAll();

    List<Product> findByCategory(Category category);

    Product insert(Product product);

    Product update(Product product);

    Optional<Product> findById(UUID productId);

    Optional<Product> findByName(String productName);

    void deleteAll();
}
