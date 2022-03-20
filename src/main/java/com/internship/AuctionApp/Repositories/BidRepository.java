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

    @Query("SELECT b FROM Bid b WHERE b.productId = :productId")
    List<Bid> findAllByProductId(@Param("productId") Product productId);

}
