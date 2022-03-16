package com.internship.AuctionApp.DTOs;


public class BidDTO {
    private Long latestBidderId;
    private float highestBid;
    private int numberOfBids;

    public BidDTO(Long latestBidderId, float highestBid, int numberOfBids) {
        this.latestBidderId = latestBidderId;
        this.highestBid = highestBid;
        this.numberOfBids = numberOfBids;
    }

    public Long getLatestBidderId() {
        return latestBidderId;
    }

    public float getHighestBid() {
        return highestBid;
    }

    public int getNumberOfBids() {
        return numberOfBids;
    }
}
