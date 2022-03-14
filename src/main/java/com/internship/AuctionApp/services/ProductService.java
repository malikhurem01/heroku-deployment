package com.internship.AuctionApp.services;

import com.internship.AuctionApp.Models.Product;

import java.util.List;

public interface ProductService {
    public Product getProduct(Long id);

    public Product createProduct(Product product);

    public List<Product> findAllProducts();
}
