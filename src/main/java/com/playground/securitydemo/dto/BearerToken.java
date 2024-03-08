package com.playground.securitydemo.dto;

import lombok.Data;

@Data // Lombok annotation to generate getter, setter, equals, hash, and toString methods.
public class BearerToken {

    private String accessToken ; // The access token for authentication.
    private String tokenType ; // The type of the token.

    // Constructor that sets the access token and token type.
    public BearerToken(String accessToken , String tokenType) {
        this.tokenType = tokenType ;
        this.accessToken = accessToken;
    }
}