package com.internship.AuctionApp.configuration;

import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class AuthWhitelistConfig {
    private static List<String> authWhitelist;

    public AuthWhitelistConfig() {
        authWhitelist = Arrays.asList(
                "/api/v1/registration",
                "/api/v1/authenticate",
                "/api/v1/get/products",
                "/api/v1/get/product/**",
                "/api/v1/auth/refresh"
        );
    }

    public static boolean isWhitelistRoute(String route) {
        return authWhitelist.contains(route);
    }

    public static List<String> getAuthWhitelist() {
        return authWhitelist;
    }
}
