package com.internship.AuctionApp.DTOs;

import com.internship.AuctionApp.Models.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

public class ProductPaginationDTO {
    private Page<Product> products;

    public ProductPaginationDTO(Page<Product> products) {
        this.products = products;
    }

    public Page<Product> getProducts() {
        return products;
    }

    public void setProducts(Page<Product> products) {
        this.products = products;
    }
}
