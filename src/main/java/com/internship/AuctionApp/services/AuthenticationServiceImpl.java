package com.internship.AuctionApp.services;

import com.internship.AuctionApp.DTOs.UserDTO;
import com.internship.AuctionApp.Exceptions.PasswordNotValidException;
import com.internship.AuctionApp.Exceptions.ServiceException;
import com.internship.AuctionApp.Exceptions.UserExistsException;
import com.internship.AuctionApp.authentication.UserCreateRequest;
import com.internship.AuctionApp.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AuthenticationServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public UserDTO registerUser(final UserCreateRequest request) throws PasswordNotValidException, UserExistsException {
        User newUser = new User.UserBuilder()
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setPassword(request.getPassword())
                .setEmail(request.getEmail())
                .setCreatedAt(new Timestamp(System.currentTimeMillis()))
                .setUpdatedAt(new Timestamp(System.currentTimeMillis()))
                .build();
        try {
             return userServiceImpl.registerUser(newUser);
        } catch (PasswordNotValidException e) {
            throw new PasswordNotValidException(e.getMessage());
        } catch (UserExistsException e) {
            throw new UserExistsException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
