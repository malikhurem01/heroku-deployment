package com.internship.AuctionApp.Exceptions;

public class ProductDoesNotExistException extends Exception {
    public ProductDoesNotExistException(final String message) {
        super(message);
    }
    public ProductDoesNotExistException(){
        super("Product does not exist.");
    }
}
