package com.internship.AuctionApp.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "api")
public class AuthWhitelistConfig {

    private String route;
    private List<String> authWhitelist;

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setAuthWhitelist(List<String> authWhitelist) {
        this.authWhitelist = authWhitelist;
    }

    public List<String> getAuthWhitelist() {
        return authWhitelist;
    }

    public boolean isWhitelistRoute(String route) {
        return authWhitelist.contains(route);
    }

    public void initializeRoutes() {
        authWhitelist = Arrays.asList(
                this.route + "/registration",
                this.route + "/authenticate",
                this.route + "/get/products",
                this.route + "/get/product",
                this.route + "/auth/refresh",
                this.route + "/get/categories",
                this.route + "/get/products/subcategory"
        );
    }
}
