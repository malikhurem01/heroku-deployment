package com.internship.AuctionApp.services;

import com.internship.AuctionApp.Models.Product;
import com.internship.AuctionApp.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProduct(final Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.get();
    }

    @Override
    public Page<Product> findAllProductsWithPagination(final int offset, final int pageSize, final String sort) {
        return productRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(Sort.Direction.ASC, sort)));
    }
}
