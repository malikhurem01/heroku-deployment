package com.internship.AuctionApp.DTOs;

public class JwtDTO {
    private String JWT_access;
    private String JWT_refresh;

    public JwtDTO(String JWT_access, String JWT_refresh) {
        this.JWT_access = JWT_access;
        this.JWT_refresh = JWT_refresh;
    }

    public String getJWT_access() {
        return JWT_access;
    }

    public void setJWT_access(String JWT_access) {
        this.JWT_access = JWT_access;
    }

    public String getJWT_refresh() {
        return JWT_refresh;
    }

    public void setJWT_refresh(String JWT_refresh) {
        this.JWT_refresh = JWT_refresh;
    }
}
