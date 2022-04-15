package com.internship.AuctionApp.Exceptions;

public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super("User with that email already exists");
    }

    public UserExistsException(String message) {
        super(message);
    }
}
