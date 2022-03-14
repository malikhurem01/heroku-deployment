package com.internship.AuctionApp.services;

import com.internship.AuctionApp.authentication.UserCreateRequest;
import com.internship.AuctionApp.Models.User;

public interface AuthenticationService {
    public User registerUser(final UserCreateRequest request) throws Exception;
}
