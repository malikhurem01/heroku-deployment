package com.internship.AuctionApp.authentication;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.AuctionApp.Models.User;
import com.internship.AuctionApp.services.AuthenticationService;
import com.internship.AuctionApp.services.UserServiceImpl;
import com.internship.AuctionApp.configuration.JWTConfig;
import com.internship.AuctionApp.utils.JWTDecode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = {"http://localhost:3000"})
@Slf4j
public class AuthenticationController {

    private final static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserServiceImpl userServiceImpl;

    @Autowired
    private final AuthenticationService authenticationService;

    private final JWTConfig jwtConfig = new JWTConfig();

    @GetMapping(path = "/auth/users/current")
    @CrossOrigin(origins = {"http://localhost:3000"})
    public ResponseEntity<?> getUserWithToken(final HttpServletRequest request) throws UsernameNotFoundException {
        final DecodedJWT decodedJWT = JWTDecode.verifyToken(request.getHeader(AUTHORIZATION));
        final String username = decodedJWT.getSubject();
        User user = null;
        try {
            user = userServiceImpl.loadUserByUsername(username);
        } catch (UsernameNotFoundException exc) {
            logger.error(exc.getMessage());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(new AuthenticationResponse(user));
    }

    @PostMapping(path = "/authenticate")
    @CrossOrigin(origins = {"http://localhost:3000"})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody final AuthenticationRequest loginRequest,
                                                       final HttpServletRequest request)
            throws BadCredentialsException, Exception {


        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException exception) {
            logger.error(exception.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            return ResponseEntity.badRequest().build();
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final User user = (User) authentication.getPrincipal();

        final String access_token = userServiceImpl.generateToken(user.getUsername(),
                jwtConfig.getAccessTokenExpireTime(), request.getRequestURL().toString());
        final String refresh_token = userServiceImpl.generateToken(user.getUsername(),
                jwtConfig.getRefreshTokenExpireTime(), request.getRequestURL().toString());

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.ok().headers(httpHeaders).body(new AuthenticationResponse(access_token, refresh_token));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refreshToken(final HttpServletRequest request,
                                          final HttpServletResponse response) throws IOException {
        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        String refresh_token = null, new_access_token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            try {
                final DecodedJWT decodedJWT = JWTDecode.verifyToken(authorizationHeader);
                final String username = decodedJWT.getSubject();
                final User user = userServiceImpl.loadUserByUsername(username);
                new_access_token = userServiceImpl.generateToken(user.getUsername(),
                        jwtConfig.getAccessTokenExpireTime(), request.getRequestURL().toString());
                refresh_token = authorizationHeader.substring(jwtConfig.getTokenPrefix().length());
            } catch (Exception exc) {
                logger.error(exc.getMessage());
                response.setHeader(HttpHeaders.EXPIRES, exc.getMessage());
                response.setStatus(FORBIDDEN.value());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), exc.getMessage());
            }
        } else {
            logger.error("Refresh token not found");
            throw new RuntimeException("Refresh token not found!");
        }
        return ResponseEntity.ok().body(new AuthenticationResponse(new_access_token, refresh_token));
    }

    @PostMapping(path = "/registration")
    public User register(@RequestBody final UserCreateRequest request) {
        return authenticationService.registerUser(request);
    }

}
