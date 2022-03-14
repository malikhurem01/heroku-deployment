package com.internship.AuctionApp.Repositories;

import com.internship.AuctionApp.Models.Bid;
import com.internship.AuctionApp.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query("SELECT b FROM Bid b WHERE b.product_id = :product_id")
    List<Bid> findAllByProduct_id(@Param("product_id") Product product_id);

}
