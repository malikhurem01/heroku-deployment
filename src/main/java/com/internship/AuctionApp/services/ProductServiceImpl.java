package com.internship.AuctionApp.services;

import com.internship.AuctionApp.DTOs.ProductDTO;
import com.internship.AuctionApp.Exceptions.EntityNotFoundException;
import com.internship.AuctionApp.Exceptions.ServiceException;
import com.internship.AuctionApp.Models.Product;
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

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SubcategoryRepository subcategoryRepository;

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
    public ProductFilterResponse filter(ProductFilterRequest productFilterRequest, final String filter) {
        try {
            Page<Product> productPage = null;
            if(filter.equals("all")) {
                productPage = productRepository.findAll(
                        PageRequest.of(productFilterRequest.getOffset(),
                                        productFilterRequest.getPageSize())
                                .withSort(Sort.by(Sort.Direction.ASC, productFilterRequest.getSort())));
            }else if(filter.equals("category")){
                productPage = productRepository.findAllByCategoryId(productFilterRequest.getId(),
                        PageRequest.of(productFilterRequest.getOffset(),
                                        productFilterRequest.getPageSize())
                                .withSort(Sort.by(Sort.Direction.ASC, productFilterRequest.getSort())));
            }else if(filter.equals("subcategory")){
                productPage = productRepository.findAllBySubcategoryId(productFilterRequest.getId(),
                        PageRequest.of(productFilterRequest.getOffset(),
                                        productFilterRequest.getPageSize())
                                .withSort(Sort.by(Sort.Direction.ASC, productFilterRequest.getSort())));
            }
            final List<ProductDTO> productDTOList = productPage
                    .stream()
                    .map(product -> new ProductDTO(product)).toList();
            final ProductFilterResponse productFilterResponse = new ProductFilterResponse(
                    productDTOList,
                    productFilterRequest.getPageSize(),
                    productDTOList.size());
            return productFilterResponse;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
