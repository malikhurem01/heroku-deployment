package com.internship.AuctionApp.services;

import com.internship.AuctionApp.Models.Product;
import com.internship.AuctionApp.Repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Override
    public Product getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.get();
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }


}
