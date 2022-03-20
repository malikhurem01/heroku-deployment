package com.internship.AuctionApp.Authentication;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
public class UserCreateRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
}
