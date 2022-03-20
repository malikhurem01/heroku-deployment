package com.internship.AuctionApp.services;

import com.internship.AuctionApp.Bids.BidCreateRequest;
import com.internship.AuctionApp.DTOs.BidHistoryDTO;
import com.internship.AuctionApp.Exceptions.EntityNotFoundException;
import com.internship.AuctionApp.Models.Bid;
import com.internship.AuctionApp.Models.Product;

import java.util.List;

public interface BidService {
    public Bid createBid(BidCreateRequest bidCreateRequest, String authorizationHeader) throws Exception;

    public List<Bid> findAllBidByProductId(Product productId);

    public BidHistoryDTO getBidHistory(Long productId) throws Exception, EntityNotFoundException;
}
