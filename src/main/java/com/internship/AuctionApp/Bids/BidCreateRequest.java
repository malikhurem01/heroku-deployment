package com.internship.AuctionApp.Bids;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BidCreateRequest {
    private long productId;
    private float bidPrice;

    public long getProductId() {
        return productId;
    }

    public float getBidPrice() {
        return bidPrice;
    }

}
