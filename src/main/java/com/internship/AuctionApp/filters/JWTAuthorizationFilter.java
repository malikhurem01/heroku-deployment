package com.internship.AuctionApp.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.AuctionApp.configuration.AuthWhitelistConfig;
import com.internship.AuctionApp.configuration.JWTConfig;
import com.internship.AuctionApp.utils.JWTDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private JWTConfig jwtConfig;
    private AuthWhitelistConfig authWhitelistConfig;

    @Autowired
    public JWTAuthorizationFilter(JWTConfig jwtConfig, AuthWhitelistConfig authWhitelistConfig) {
        this.jwtConfig = jwtConfig;
        this.authWhitelistConfig = authWhitelistConfig;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Expose-Headers", "Date");
        authWhitelistConfig.initializeRoutes();
        if (authWhitelistConfig.isWhitelistRoute(request.getServletPath())) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
                try {
                    final DecodedJWT decodedJWT = JWTDecode.verifyToken(authorizationHeader, jwtConfig.getTokenPrefix());
                    final String username = decodedJWT.getSubject();
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception exc) {
                    logger.error(exc.getMessage());
                    response.setHeader("error", exc.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
