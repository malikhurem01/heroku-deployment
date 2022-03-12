package com.internship.AuctionApp.services;

import com.internship.AuctionApp.Models.Bid;
import com.internship.AuctionApp.Models.Product;

import java.util.List;

public interface BidService {
    public Bid saveBid(Bid bid);

    public List<Bid> findAllBidByProductId(Product product_id);
}
