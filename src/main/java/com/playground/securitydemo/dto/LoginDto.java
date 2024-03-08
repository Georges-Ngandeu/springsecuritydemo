package com.playground.securitydemo.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data // Lombok annotation to generate getter, setter, equals, hash, and toString methods.
@FieldDefaults(level = AccessLevel.PRIVATE) // Lombok annotation to set the access level of class fields, in this case, to private.
public class LoginDto {

    private String email ; // The email of the user trying to log in.
    private String password ; // The password of the user trying to log in.
}