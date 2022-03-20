package com.internship.AuctionApp.utils;

import com.auth0.jwt.algorithms.Algorithm;
import com.internship.AuctionApp.Configuration.JWTConfig;
import org.springframework.context.annotation.Bean;

public class JWTSignAlgorithm {

    private final JWTConfig jwtConfig = new JWTConfig();

    @Bean
    public static final Algorithm getAlgorithmSignature() {
        return Algorithm.HMAC256("supersecret".getBytes());
    }
}
