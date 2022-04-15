package com.internship.AuctionApp.Exceptions;

public class JWTException extends Exception{
    public JWTException() {
        super("JSON Web Token not valid.");
    }

    public JWTException(String message) {
        super(message);
    }
}
