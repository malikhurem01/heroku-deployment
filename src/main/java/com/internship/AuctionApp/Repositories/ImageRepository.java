package com.internship.AuctionApp.Repositories;

import com.internship.AuctionApp.Models.Image;
import com.internship.AuctionApp.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT b FROM Image b WHERE b.product_id = :product_id")
    List<Image> findAllByProduct_id(@Param("product_id") Product product_id);

}
