package com.internship.AuctionApp.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.internship.AuctionApp.configuration.JWTConfig;
import org.springframework.beans.factory.annotation.Autowired;

public class JWTDecode {

    @Autowired
    private static JWTConfig jwtConfig;

    public static DecodedJWT verifyToken(String authorizationHeaderToken, String prefix) {
        final com.auth0.jwt.JWTVerifier verifier = JWT.require(com.internship.AuctionApp.utils.JWTSignAlgorithm.getAlgorithmSignature()).build();
        final String token = authorizationHeaderToken.substring(prefix.length()+1);
        final DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }
}
