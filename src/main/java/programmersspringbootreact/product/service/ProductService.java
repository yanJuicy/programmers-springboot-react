package programmersspringbootreact.product.service;

import programmersspringbootreact.product.model.Category;
import programmersspringbootreact.product.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductsByCategory(Category category);

    List<Product> getAllProducts();

    Product createProduct(String productName, Category category, long price, String description);
}
