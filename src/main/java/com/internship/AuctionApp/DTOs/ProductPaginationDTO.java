package com.internship.AuctionApp.DTOs;

import com.internship.AuctionApp.Models.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class ProductPaginationDTO {
    private Page<Product> products;

    public ProductPaginationDTO(Page<Product> products) {
        this.products = products;
    }
}
