package com.internship.AuctionApp.authentication;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class UserCreateRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
}
