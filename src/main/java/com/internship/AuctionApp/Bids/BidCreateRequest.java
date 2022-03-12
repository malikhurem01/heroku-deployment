package com.internship.AuctionApp.Bids;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class BidCreateRequest {
    private long product_id;
    private float bid_price;

    public long getProduct_id() {
        return product_id;
    }

    public float getBid_price() {
        return bid_price;
    }

}
