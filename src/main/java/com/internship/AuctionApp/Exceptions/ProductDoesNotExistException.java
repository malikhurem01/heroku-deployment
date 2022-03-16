package com.internship.AuctionApp.Exceptions;

public class ProductDoesNotExistException extends Exception {
    public ProductDoesNotExistException(){
        super("Product does not exist.");
    }

    public ProductDoesNotExistException(final String message) {
        super(message);
    }
}
