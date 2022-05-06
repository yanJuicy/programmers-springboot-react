package programmersspringbootreact.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import programmersspringbootreact.product.controller.CreateProductRequestDto;
import programmersspringbootreact.product.controller.ProductController;
import programmersspringbootreact.product.model.Category;
import programmersspringbootreact.product.service.ProductService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void productsPageTest() throws Exception {
        String url = "/products";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")));
    }

    @Test
    void newPageTest() throws Exception {
        String url = "/new-product";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")));
    }

    @Test
    void productCreateTest() throws Exception {
        CreateProductRequestDto createRequest = new CreateProductRequestDto("제목", Category.COFFEE_BEAN_PACKAGE, 2000L, "내용");
        String url = "/products";

        mockMvc.perform(post(url)
                        .contentType("application/x-www-form-urlencoded")
                        .accept("application/x-www-form-urlencoded")
                        .content(objectMapper.writeValueAsBytes(createRequest)))
                .andExpect(status().is3xxRedirection());

    }

}