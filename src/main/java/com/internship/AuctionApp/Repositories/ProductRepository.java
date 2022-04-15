package com.internship.AuctionApp.Repositories;

import com.internship.AuctionApp.Models.Category;
import com.internship.AuctionApp.Models.Product;

import com.internship.AuctionApp.Models.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public Page<Product> findAllByCategoryInAndStartPriceBetween(Pageable pageable,
                                             List<Category> categories, float priceMin, float priceMax);

    public Page<Product>
    findAllByCategoryInOrSubcategoryInAndStartPriceBetween(Pageable pageable,
                                                           List<Category> categories,
                                                           List<Subcategory> subcategories,
                                                           float priceMin,
                                                           float priceMax);

    public Page<Product>
    findAllBySubcategoryInAndStartPriceBetween(Pageable pageable,
                                               List<Subcategory> subcategories,
                                               float priceMin,
                                               float priceMax);

    public Page<Product> findAllByStartPriceBetween(Pageable pageable,
                                                    float priceMin,
                                                    float priceMax);
}
