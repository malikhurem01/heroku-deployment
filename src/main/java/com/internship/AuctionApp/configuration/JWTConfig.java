package com.internship.AuctionApp.configuration;

public class JWTConfig {
    private final String secret = "supersecret";
    private final String tokenPrefix = "Bearer ";
    private final int accessTokenExpireTime = 180; // 3 hours
    private final int refreshTokenExpireTime = 900; // 15 hours

    public final String getSecret() {
        return secret;
    }

    public final String getTokenPrefix() {
        return tokenPrefix;
    }

    public final int getAccessTokenExpireTime() {
        return accessTokenExpireTime;
    }

    public final int getRefreshTokenExpireTime() {
        return refreshTokenExpireTime;
    }

}
