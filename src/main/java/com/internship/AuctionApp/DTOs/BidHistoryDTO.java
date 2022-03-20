package com.internship.AuctionApp.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidHistoryDTO {
    private Long latestBidderId;
    private float highestBid;
    private int numberOfBids;

    public BidHistoryDTO(Long latestBidderId, float highestBid, int numberOfBids) {
        this.latestBidderId = latestBidderId;
        this.highestBid = highestBid;
        this.numberOfBids = numberOfBids;
    }

}
