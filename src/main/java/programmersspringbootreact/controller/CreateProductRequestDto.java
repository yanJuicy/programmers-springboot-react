package programmersspringbootreact.controller;

import programmersspringbootreact.model.Category;

public class CreateProductRequestDto {
    private String productName;
    private Category category;
    private long price;
    private String description;

    public CreateProductRequestDto(String productName, Category category, long price, String description) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public Category getCategory() {
        return category;
    }

    public long getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
