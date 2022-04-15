package com.internship.AuctionApp.services;

import com.internship.AuctionApp.DTOs.ProductDTO;
import com.internship.AuctionApp.Exceptions.EntityNotFoundException;
import com.internship.AuctionApp.Exceptions.ServiceException;
import com.internship.AuctionApp.Models.Category;
import com.internship.AuctionApp.Models.Product;
import com.internship.AuctionApp.Models.Subcategory;
import com.internship.AuctionApp.Products.ProductFilterRequest;
import com.internship.AuctionApp.Products.ProductFilterResponse;
import com.internship.AuctionApp.Repositories.CategoryRepository;
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
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    private final int PRODUCTS_PER_PAGE = 8;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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
    public ProductFilterResponse filter(ProductFilterRequest productFilterRequest) {
        Sort.Direction sortDirection = null;
        if (productFilterRequest.getDirection() == 0) {
            sortDirection = Sort.Direction.ASC;
        } else {
            sortDirection = Sort.Direction.DESC;
        }
        try {
            Page<Product> productPage = null;
            if (!productFilterRequest.getCategory().isEmpty()) {
                List<Long> categoriesList = productFilterRequest
                        .getCategory()
                        .stream()
                        .map(integer -> Long.valueOf(integer))
                        .collect(Collectors.toList());
                List<Category> categories = categoryRepository.findAllByIdIn(categoriesList);
                if (!productFilterRequest.getSubcategories().isEmpty()) {
                    List<Long> subcategoriesList = productFilterRequest
                            .getSubcategories()
                            .stream()
                            .map(integer -> Long.valueOf(integer))
                            .collect(Collectors.toList());
                    List<Subcategory> subcategories = subcategoryRepository.findAllByIdIn(subcategoriesList);
                    productPage = productRepository
                            .findAllByCategoryInOrSubcategoryInAndStartPriceBetween(
                                    PageRequest.of(productFilterRequest.getOffset(),
                                                    PRODUCTS_PER_PAGE)
                                            .withSort(Sort.by(sortDirection, productFilterRequest.getSort())),
                                    categories, subcategories, productFilterRequest.getPriceMin(), productFilterRequest.getPriceMax());
                } else {
                    productPage = productRepository.findAllByCategoryInAndStartPriceBetween(PageRequest.of(productFilterRequest.getOffset(),
                                    PRODUCTS_PER_PAGE)
                            .withSort(Sort.by(sortDirection, productFilterRequest.getSort())),
                            categories,
                            productFilterRequest.getPriceMin(),
                            productFilterRequest.getPriceMax());
                }
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
}
