package com.internship.AuctionApp.services;

import com.internship.AuctionApp.Exceptions.PasswordNotValidException;
import com.internship.AuctionApp.Exceptions.UserExistsException;
import com.internship.AuctionApp.authentication.UserCreateRequest;
import com.internship.AuctionApp.Models.User;
import com.internship.AuctionApp.utils.ValidatePassword;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.sql.Timestamp;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserServiceImpl userServiceImpl;

    @Override
    public User registerUser(final UserCreateRequest request) throws PasswordNotValidException, UserExistsException {
        User newUser = new User.UserBuilder()
                .setFirst_name(request.getFirst_name())
                .setLast_name(request.getLast_name())
                .setPassword(request.getPassword())
                .setEmail(request.getEmail())
                .setCreated_at(new Timestamp(System.currentTimeMillis()))
                .setUpdated_at(new Timestamp(System.currentTimeMillis()))
                .build();
        User registeredUser = null;
        try {
            registeredUser = userServiceImpl.registerUser(newUser);
        } catch (PasswordNotValidException exception) {
            throw new PasswordNotValidException();
        } catch (UserExistsException exception) {
            throw new UserExistsException();
        } catch (RuntimeException exception) {
            throw new RuntimeException(exception.getMessage());
        }
        return registeredUser;
    }
}
