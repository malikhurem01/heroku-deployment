package com.internship.AuctionApp.services;

import com.auth0.jwt.JWT;
import com.internship.AuctionApp.DTOs.UserDTO;
import com.internship.AuctionApp.Exceptions.JWTException;
import com.internship.AuctionApp.Exceptions.PasswordNotValidException;
import com.internship.AuctionApp.Exceptions.ServiceException;
import com.internship.AuctionApp.Exceptions.UserExistsException;
import com.internship.AuctionApp.Models.User;
import com.internship.AuctionApp.Repositories.AppUserRepository;
import com.internship.AuctionApp.security.PasswordConfig;
import com.internship.AuctionApp.utils.JWTSignAlgorithm;
import com.internship.AuctionApp.utils.ValidatePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final AppUserRepository appUserRepository;
    private final PasswordConfig passwordConfig;
    private final String USER_NOT_FOUND_MESSAGE = "User with email %s not found.";

    @Autowired
    public UserServiceImpl(AppUserRepository appUserRepository, PasswordConfig passwordConfig) {
        this.appUserRepository = appUserRepository;
        this.passwordConfig = passwordConfig;
    }

    @Override
    public User loadUserByUsername(final String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    @Override
    public UserDTO retrieveUser(final String email) throws Exception {
        return new UserDTO(this.loadUserByUsername(email));
    }

    @Override
    public UserDTO registerUser(final User user) throws UserExistsException, PasswordNotValidException, ServiceException {
        final Boolean exists = appUserRepository.findByEmail(user.getEmail()).isPresent();
        if (exists) {
            throw new UserExistsException("User already exists");
        }
        final Boolean isValid = ValidatePassword.isValid(user.getPassword());
        if (!isValid) {
            throw new PasswordNotValidException("Password or email not valid");
        }
        final String encodedPassword = passwordConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        try {
            User registeredUser = appUserRepository.save(user);
            return new UserDTO(registeredUser);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public String generateToken(final String subject, final int expirationMinutes, final String issuer) throws JWTException {
        try {
            return JWT.create()
                    .withSubject(subject)
                    .withExpiresAt(new Date(System.currentTimeMillis() + expirationMinutes * 60 * 1000))
                    .withIssuer(issuer)
                    .sign(JWTSignAlgorithm.getAlgorithmSignature());
        } catch (Exception e) {
            throw new JWTException(e.getMessage());
        }
    }
}
