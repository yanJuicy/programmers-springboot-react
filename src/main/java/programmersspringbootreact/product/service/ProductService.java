package programmersspringbootreact.product.service;

import programmersspringbootreact.product.model.Category;
import programmersspringbootreact.product.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<Product> getProductsByCategory(Category category);

    List<Product> getAllProducts();

    Product createProduct(String productName, Category category, long price, String description);

    void deleteById(UUID uuid);

    Product getProductById(UUID productId);

    Product updateProduct(Product product);
}
