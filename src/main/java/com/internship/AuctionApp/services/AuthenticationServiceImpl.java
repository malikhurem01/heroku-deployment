package com.internship.AuctionApp.services;

import com.internship.AuctionApp.authentication.UserCreateRequest;
import com.internship.AuctionApp.Models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserServiceImpl userServiceImpl;

    @Override
    public User registerUser(final UserCreateRequest request) {
        User newUser = null;
        User registeredUser = null;
        try {
            newUser = new User.UserBuilder()
                    .setFirst_name(request.getFirst_name())
                    .setLast_name(request.getLast_name())
                    .setPassword(request.getPassword())
                    .setEmail(request.getEmail())
                    .setCreated_at(new Timestamp(System.currentTimeMillis()))
                    .setUpdated_at(new Timestamp(System.currentTimeMillis()))
                    .build();

            registeredUser = userServiceImpl.registerUser(newUser);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return registeredUser;
    }
}
