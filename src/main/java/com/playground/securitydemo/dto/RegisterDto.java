package com.playground.securitydemo.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data // Lombok annotation to generate getter, setter, equals, hash, and toString methods.
@FieldDefaults(level = AccessLevel.PRIVATE) // Lombok annotation to set the access level of class fields, in this case, to private.
public class RegisterDto implements Serializable {

    String firstName ; // The first name of the user trying to register.
    String lastName ; // The last name of the user trying to register.
    String email; // The email of the user trying to register.
    String password ; // The password of the user trying to register.
    String userRole ; // The role of the user trying to register.
}