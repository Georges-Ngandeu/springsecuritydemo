package com.playground.securitydemo.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity // This annotation specifies that the class is an entity and is mapped to a database table.
@Getter @Setter // Lombok annotations to auto-generate getters and setters.
@NoArgsConstructor // Lombok annotation to generate a no-args constructor.
@AllArgsConstructor // Lombok annotation to generate a constructor with all properties.
@FieldDefaults(level = AccessLevel.PRIVATE) // Lombok annotation to set the access level of class fields, in this case, to private.
public class Role implements Serializable  { // The class implements Serializable, which means it can be converted to a byte stream and restored later.

    @Id // This annotation specifies the primary key of an entity.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This annotation provides for the specification of generation strategies for the values of primary keys.
    Integer id ; // The primary key.

    @Enumerated(EnumType.STRING) // This annotation specifies that a persistent property or field should be persisted as a enumerated type, in this case as a String.
    RoleName roleName ; // The role name.

    public Role (RoleName roleName) {this.roleName = roleName;} // Constructor that sets the role name.

    public String getRoleName() { // Getter for the role name.
        return roleName.toString(); // Returns the role name as a string.
    }
}