package com.internship.AuctionApp.Exceptions;

public class BidRequestNotValidException extends Exception {
    public BidRequestNotValidException() {
        super("Bid request not valid.");
    }

    public BidRequestNotValidException(String message) {
        super(message);
    }
}
