package com.internship.AuctionApp.utils;

import com.internship.AuctionApp.Exceptions.PasswordNotValidException;

import java.util.regex.Pattern;

public class ValidatePassword {

    public static Boolean isValid(String password) throws PasswordNotValidException {
        Boolean isMatch = Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$", password);
        if (!isMatch) {
            throw new PasswordNotValidException();
        }
        return true;
    }

}
