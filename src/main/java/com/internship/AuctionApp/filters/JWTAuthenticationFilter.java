package com.internship.AuctionApp.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.AuctionApp.Models.User;
import com.internship.AuctionApp.configuration.JWTConfig;
import com.internship.AuctionApp.services.UserServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final UserServiceImpl userServiceImpl;

    private final JWTConfig jwtConfig = new JWTConfig();

    private final String ACCESS_TOKEN = "access_token";
    private final String REFRESH_TOKEN = "refresh_token";

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserServiceImpl userServiceImpl) {
        this.authenticationManager = authenticationManager;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        final User user = (User) authentication.getPrincipal();

        final String access_token = userServiceImpl.generateToken(user.getUsername(),
                jwtConfig.getAccessTokenExpireTime(),
                request.getRequestURL().toString());

        final String refresh_token = userServiceImpl.generateToken(user.getUsername(),
                jwtConfig.getRefreshTokenExpireTime(),
                request.getRequestURL().toString());

        final Map<String, String> tokens = new HashMap<>();
        tokens.put(ACCESS_TOKEN, access_token);
        tokens.put(REFRESH_TOKEN, refresh_token);

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
