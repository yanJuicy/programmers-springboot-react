package programmersspringbootreact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import programmersspringbootreact.service.ProductService;

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

}
