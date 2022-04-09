package com.internship.AuctionApp.services;

import com.internship.AuctionApp.DTOs.ProductDTO;
import com.internship.AuctionApp.Exceptions.EntityNotFoundException;
import com.internship.AuctionApp.Exceptions.ServiceException;
import com.internship.AuctionApp.Models.Product;
import com.internship.AuctionApp.Models.Subcategory;
import com.internship.AuctionApp.Products.FilterRequest;
import com.internship.AuctionApp.Products.ProductFilterRequest;
import com.internship.AuctionApp.Products.ProductFilterResponse;
import com.internship.AuctionApp.Repositories.ProductRepository;
import com.internship.AuctionApp.Repositories.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SubcategoryRepository subcategoryRepository;

    private final int PRODUCTS_PER_PAGE = 8;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, SubcategoryRepository subcategoryRepository) {
        this.productRepository = productRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    @Override
    public Product getProduct(final Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product == null) {
            throw new EntityNotFoundException("Product");
        }
        return product.get();
    }

    @Override
    public ProductFilterResponse filter(FilterRequest productFilterRequest) {
        Sort.Direction sortDirection = null;
        if (productFilterRequest.getDirection() == 0) {
            sortDirection = Sort.Direction.ASC;
        } else {
            sortDirection = Sort.Direction.DESC;
        }
        try {
            Page<Product> productPage = null;
            if (productFilterRequest.getCategory() != null) {
                productPage = productRepository.findAllByCategoryId(Long.valueOf(productFilterRequest.getCategory()),
                        PageRequest.of(productFilterRequest.getOffset(),
                                        PRODUCTS_PER_PAGE)
                                .withSort(Sort.by(sortDirection, productFilterRequest.getSort())));
            } else if (!productFilterRequest.getSubcategories().isEmpty()) {
                List<Long> subcategoriesList = productFilterRequest
                        .getSubcategories()
                        .stream()
                        .map(integer -> Long.valueOf(integer))
                        .collect(Collectors.toList());
                List<Subcategory> subcategories = subcategoryRepository.findAllByIdIn(subcategoriesList);
                productPage = productRepository.findAllBySubcategoryInAndStartPriceBetween(
                        PageRequest.of(
                                        productFilterRequest.getOffset(), PRODUCTS_PER_PAGE)
                                .withSort(Sort.by(sortDirection, productFilterRequest.getSort())),
                        subcategories, productFilterRequest.getPriceMin(), productFilterRequest.getPriceMax());
            } else if (productFilterRequest.getPriceMin() > 0 || productFilterRequest.getPriceMax() > 0) {
                productPage = productRepository.findAllByStartPriceBetween(
                        PageRequest.of(
                                        productFilterRequest.getOffset(), PRODUCTS_PER_PAGE)
                                .withSort(Sort.by(sortDirection, productFilterRequest.getSort())),
                        productFilterRequest.getPriceMin(),
                        productFilterRequest.getPriceMax());
            } else {
                productPage = productRepository.findAll(
                        PageRequest.of(productFilterRequest.getOffset(),
                                        PRODUCTS_PER_PAGE)
                                .withSort(Sort.by(sortDirection, productFilterRequest.getSort())));
            }
            final List<ProductDTO> products = productPage
                    .stream()
                    .map(product -> new ProductDTO(product)).toList();
            final ProductFilterResponse productFilterResponse = new ProductFilterResponse(
                    products,
                    PRODUCTS_PER_PAGE,
                    products.size());
            return productFilterResponse;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public ProductFilterResponse filter(ProductFilterRequest productFilterRequest, final String filter) {
        try {
            Page<Product> productPage = null;
            if (filter.equals("all")) {
                productPage = productRepository.findAll(
                        PageRequest.of(productFilterRequest.getOffset(),
                                        productFilterRequest.getPageSize())
                                .withSort(Sort.by(Sort.Direction.ASC, productFilterRequest.getSort())));
            } else if (filter.equals("category")) {
                productPage = productRepository.findAllByCategoryId(productFilterRequest.getId(),
                        PageRequest.of(productFilterRequest.getOffset(),
                                        productFilterRequest.getPageSize())
                                .withSort(Sort.by(Sort.Direction.ASC, productFilterRequest.getSort())));
            } else if (filter.equals("subcategory")) {
                productPage = productRepository.findAllBySubcategoryId(productFilterRequest.getId(),
                        PageRequest.of(productFilterRequest.getOffset(),
                                        productFilterRequest.getPageSize())
                                .withSort(Sort.by(Sort.Direction.ASC, productFilterRequest.getSort())));
            }
            final List<ProductDTO> products = productPage
                    .stream()
                    .map(product -> new ProductDTO(product)).toList();
            final ProductFilterResponse productFilterResponse = new ProductFilterResponse(
                    products,
                    productFilterRequest.getPageSize(),
                    products.size());
            return productFilterResponse;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
