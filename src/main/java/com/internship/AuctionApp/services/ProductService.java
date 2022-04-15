package com.internship.AuctionApp.services;

import com.internship.AuctionApp.Models.Product;
import com.internship.AuctionApp.Products.ProductFilterRequest;
import com.internship.AuctionApp.Products.ProductFilterResponse;

public interface ProductService {
    Product getProduct(Long id);

    ProductFilterResponse filter(ProductFilterRequest productFilterRequest);
}
