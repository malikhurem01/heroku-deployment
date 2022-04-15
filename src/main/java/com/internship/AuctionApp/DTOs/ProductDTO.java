package com.internship.AuctionApp.DTOs;

import com.internship.AuctionApp.Models.Product;

public class ProductDTO {
    private Product product;

    public ProductDTO(Product product) {
        this.product = product;
    }

    public ProductDTO() {

    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
