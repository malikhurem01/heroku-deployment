package com.internship.AuctionApp.services;

import com.internship.AuctionApp.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductService {
    public Product getProduct(Long id);

    public Page<Product> findAllProductsWithPagination(int offset, int pageSize, String sort);
}
