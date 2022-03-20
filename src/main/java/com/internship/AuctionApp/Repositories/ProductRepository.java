package com.internship.AuctionApp.Repositories;

import com.internship.AuctionApp.Models.Product;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT a FROM Product a ORDER BY a.createdAt ASC")
    public List<Product> findAllOrderByCreatedAt();

    @Query("SELECT a FROM Product a ORDER BY a.auctionDateEnd ASC")
    public List<Product> findAllOrderByAuctionDateEnd();

}
