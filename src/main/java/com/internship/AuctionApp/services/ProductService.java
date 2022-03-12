package com.internship.AuctionApp.services;

import com.internship.AuctionApp.Models.Product;

public interface ProductService {
    public Product getProduct(Long id);

    public Product saveProduct(Product product);
}
