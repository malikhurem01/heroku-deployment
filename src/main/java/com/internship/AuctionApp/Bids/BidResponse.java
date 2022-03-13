package com.internship.AuctionApp.Bids;


public class BidResponse {
    private Long latest_bidder_id;
    private float highestBid;
    private int numberOfBids;

    public BidResponse(Long latest_bidder_id, float highestBid, int numberOfBids) {
        this.latest_bidder_id = latest_bidder_id;
        this.highestBid = highestBid;
        this.numberOfBids = numberOfBids;
    }

    public Long getLatest_bidder_id() {
        return latest_bidder_id;
    }

    public float getHighestBid() {
        return highestBid;
    }

    public int getNumberOfBids() {
        return numberOfBids;
    }
}
