package com.internship.AuctionApp.DTOs;

public class BidHistoryDTO {

    private Long latestBidderId;
    private float highestBid;
    private int numberOfBids;

    public BidHistoryDTO(Long latestBidderId, float highestBid, int numberOfBids) {
        this.latestBidderId = latestBidderId;
        this.highestBid = highestBid;
        this.numberOfBids = numberOfBids;
    }

    public Long getLatestBidderId() {
        return latestBidderId;
    }

    public void setLatestBidderId(Long latestBidderId) {
        this.latestBidderId = latestBidderId;
    }

    public float getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(float highestBid) {
        this.highestBid = highestBid;
    }

    public int getNumberOfBids() {
        return numberOfBids;
    }

    public void setNumberOfBids(int numberOfBids) {
        this.numberOfBids = numberOfBids;
    }
}
