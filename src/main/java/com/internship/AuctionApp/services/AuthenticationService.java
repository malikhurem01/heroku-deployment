package com.internship.AuctionApp.services;

import com.internship.AuctionApp.DTOs.UserDTO;
import com.internship.AuctionApp.authentication.UserCreateRequest;

public interface AuthenticationService {
    public UserDTO registerUser(final UserCreateRequest request) throws Exception;
}
