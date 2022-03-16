package com.internship.AuctionApp.services;

import com.internship.AuctionApp.Bids.BidCreateRequest;
import com.internship.AuctionApp.DTOs.BidDTO;
import com.internship.AuctionApp.Exceptions.ProductDoesNotExistException;
import com.internship.AuctionApp.Models.Bid;
import com.internship.AuctionApp.Models.Product;

import java.util.List;

public interface BidService {
    public Bid createBid(BidCreateRequest bidCreateRequest, String authorizationHeader) throws Exception;

    public List<Bid> findAllBidByProductId(Product productId);

    public BidDTO getBidHistory(Long productId) throws ProductDoesNotExistException;
}
