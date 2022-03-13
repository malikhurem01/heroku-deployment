package com.internship.AuctionApp.authentication;

import com.internship.AuctionApp.Models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {
    private String JWT_access = null;
    private String JWT_refresh = null;

    private User user = null;

    public AuthenticationResponse(String jwt_access, String jwt_refresh) {
        this.JWT_access = jwt_access;
        this.JWT_refresh = jwt_refresh;
    }

    public AuthenticationResponse(User user) {
        this.user = user;
    }

}
