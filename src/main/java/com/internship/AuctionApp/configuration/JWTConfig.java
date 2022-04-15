package com.internship.AuctionApp.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JWTConfig {

    private String secret;
    private String tokenPrefix;
    private int accessTokenExpireTime;
    private int refreshTokenExpireTime;

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public void setAccessTokenExpireTime(int accessTokenExpireTime) {
        this.accessTokenExpireTime = accessTokenExpireTime;
    }

    public void setRefreshTokenExpireTime(int refreshTokenExpireTime) {
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }

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
