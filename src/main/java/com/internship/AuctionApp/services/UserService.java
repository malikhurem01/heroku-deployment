package com.internship.AuctionApp.services;

import com.internship.AuctionApp.DTOs.UserDTO;
import com.internship.AuctionApp.Exceptions.JWTException;
import com.internship.AuctionApp.Models.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserService {

    User loadUserByUsername(final String email) throws UsernameNotFoundException;
    UserDTO registerUser(User user) throws Exception;
    String generateToken(String subject, int expirationMinutes, String issuer) throws JWTException;
    UserDTO retrieveUser(final String email) throws Exception;
}
