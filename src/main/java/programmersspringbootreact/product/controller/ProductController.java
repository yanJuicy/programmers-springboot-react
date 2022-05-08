package programmersspringbootreact.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import programmersspringbootreact.product.model.Product;
import programmersspringbootreact.product.service.ProductService;

import java.util.UUID;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String productsPage(Model model) {
        var products =  productService.getAllProducts();
        model.addAttribute("products", products);
        return "product-list";
    }

    @GetMapping("/new-product")
    public String newProductPage() {
        return "new-product";
    }

    @PostMapping("/products")
    public String productCreate(CreateProductRequestDto createProductRequest) {
        productService.createProduct(
                createProductRequest.getProductName(),
                createProductRequest.getCategory(),
                createProductRequest.getPrice(),
                createProductRequest.getDescription());
        return "redirect:/products";
    }

    @DeleteMapping("/products/{uuid}")
    public String productDelete(@PathVariable UUID uuid) {
        productService.deleteById(uuid);
        return "redirect:/products";
    }

    @GetMapping("/products/{uuid}")
    public String getProductUpdatePage(@PathVariable UUID uuid, Model model) {
        Product product = productService.getProductById(uuid);
        model.addAttribute("product", product);
        return "update-product";
    }

    @PutMapping("/products/{uuid}")
    public String productUpdate(@PathVariable UUID uuid, UpdateProductRequestDto updateProductRequestDto) {
        Product product = productService.getProductById(uuid);
        product.update(updateProductRequestDto);
        productService.updateProduct(product);
        return "redirect:/products";
    }

}
