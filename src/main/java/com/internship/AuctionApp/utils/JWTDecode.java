package com.internship.AuctionApp.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.internship.AuctionApp.Configuration.JWTConfig;

public class JWTDecode {

    private final static JWTConfig jwtConfig = new JWTConfig();

    public static DecodedJWT verifyToken(String authorizationHeaderToken) {
        final com.auth0.jwt.JWTVerifier verifier = JWT.require(com.internship.AuctionApp.utils.JWTSignAlgorithm.getAlgorithmSignature()).build();
        final String token = authorizationHeaderToken.substring(jwtConfig.getTokenPrefix().length());
        final DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }
}
