package com.internship.AuctionApp.Exceptions;

public class PasswordNotValidException extends Exception{
    public PasswordNotValidException(){
        super("Password doesn't meet the requirements");
    }
    public PasswordNotValidException(String message){
        super(message);
    }
}
