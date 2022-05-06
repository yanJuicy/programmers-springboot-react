package programmersspringbootreact.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import programmersspringbootreact.product.model.Category;
import programmersspringbootreact.product.model.Product;
import programmersspringbootreact.product.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/v1/products")
    public List<Product> productList(@RequestParam Optional<Category> category) {
        return category
                .map(productService::getProductsByCategory)
                .orElse(productService.getAllProducts());
    }
}
