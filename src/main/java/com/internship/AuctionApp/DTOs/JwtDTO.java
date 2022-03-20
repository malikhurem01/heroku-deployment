package com.internship.AuctionApp.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private String JWT_access;
    private String JWT_refresh;

    public JwtDTO(String JWT_access, String JWT_refresh) {
        this.JWT_access = JWT_access;
        this.JWT_refresh = JWT_refresh;
    }
}
