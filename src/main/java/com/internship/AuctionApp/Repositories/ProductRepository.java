package com.internship.AuctionApp.Repositories;

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

    public Page<Product> findAllBySubcategoryId(Long subcategoryId, Pageable pageable);

    public Page<Product> findAllByCategoryId(Long categoryId, Pageable pageable);

    public Page<Product> findAllBySubcategoryInAndStartPriceBetween(Pageable pageable, List<Subcategory> subcategories, float priceMin, float priceMax);

    public Page<Product> findAllByStartPriceBetween(Pageable pageable, float priceMin, float priceMax);
}
