package com.internship.AuctionApp.Exceptions;

public class ServiceException extends RuntimeException{
    public ServiceException(String message){
        super(message);
    }
    public ServiceException(){
        super("An error during runtime occured.");
    }
}
