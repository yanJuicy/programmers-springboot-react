package programmersspringbootreact.service;

import programmersspringbootreact.model.Category;
import programmersspringbootreact.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductsByCategory(Category category);

    List<Product> getAllProducts();

    Product createProduct(String productName, Category category, long price, String description);
}
