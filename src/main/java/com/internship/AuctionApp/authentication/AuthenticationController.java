package com.internship.AuctionApp.authentication;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.AuctionApp.DTOs.JwtDTO;
import com.internship.AuctionApp.DTOs.UserDTO;
import com.internship.AuctionApp.Exceptions.JWTException;
import com.internship.AuctionApp.Exceptions.PasswordNotValidException;
import com.internship.AuctionApp.Exceptions.ServiceException;
import com.internship.AuctionApp.Exceptions.UserExistsException;
import com.internship.AuctionApp.Models.User;
import com.internship.AuctionApp.services.AuthenticationService;
import com.internship.AuctionApp.services.UserService;
import com.internship.AuctionApp.services.UserServiceImpl;
import com.internship.AuctionApp.configuration.JWTConfig;
import com.internship.AuctionApp.utils.JWTDecode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = {"http://localhost:3000", "https://auction-app-internship-fr.herokuapp.com/"})
@Slf4j
public class AuthenticationController {

    private final static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final JWTConfig jwtConfig = new JWTConfig();

    @Autowired
    public AuthenticationController(final AuthenticationManager authenticationManager,
                                    final UserService userService,
                                    final AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @GetMapping(path = "/auth/users/current")
    public ResponseEntity<?> getUserWithToken(final HttpServletRequest request) throws UsernameNotFoundException {
        try {
            final DecodedJWT decodedJWT = JWTDecode.verifyToken(request.getHeader(AUTHORIZATION));
            final String username = decodedJWT.getSubject();
            return ResponseEntity.ok().body(userService.retrieveUser(username));
        } catch (UsernameNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody final AuthenticationRequest loginRequest,
                                                       final HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final User user = (User) authentication.getPrincipal();
            try {
                final String access_token = userService.generateToken(
                        user.getUsername(),
                        jwtConfig.getAccessTokenExpireTime(),
                        request.getRequestURL().toString());
                final String refresh_token = userService.generateToken(
                        user.getUsername(),
                        jwtConfig.getRefreshTokenExpireTime(),
                        request.getRequestURL().toString());
                final HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                final JwtDTO jwtDTO = new JwtDTO(access_token, refresh_token);
                return ResponseEntity.ok().headers(httpHeaders).body(jwtDTO);
            } catch (JWTException e) {
                logger.error(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                logger.error(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (BadCredentialsException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refreshToken(final HttpServletRequest request,
                                          final HttpServletResponse response) throws Exception {
        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            try {
                final DecodedJWT decodedJWT = JWTDecode.verifyToken(authorizationHeader);
                final String username = decodedJWT.getSubject();
                final User user = userService.loadUserByUsername(username);
                String new_access_token = userService.generateToken(
                        user.getUsername(),
                        jwtConfig.getAccessTokenExpireTime(),
                        request.getRequestURL().toString());
                String refresh_token = authorizationHeader.substring(jwtConfig.getTokenPrefix().length());
                final JwtDTO jwtDTO = new JwtDTO(new_access_token, refresh_token);
                return ResponseEntity.ok().body(jwtDTO);
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.setHeader(HttpHeaders.EXPIRES, e.getMessage());
                response.setStatus(FORBIDDEN.value());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), e.getMessage());
            }
        } else {
            logger.error("Refresh token not found");
            throw new Exception("Refresh token not found!");
        }
        return ResponseEntity.ok().body(new JwtDTO(null, null));
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<?> register(@RequestBody final UserCreateRequest request) throws Exception {
        try {
            UserDTO registeredUser = authenticationService.registerUser(request);
            return ResponseEntity.ok().body(registeredUser);
        } catch (PasswordNotValidException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UserExistsException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
