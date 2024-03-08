package com.playground.securitydemo.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity // This annotation specifies that the class is an entity and is mapped to a database table.
@Table(
    name = "users", // Specifies the name of the database table to be used for mapping.
    uniqueConstraints = { // Specifies that a combination of columns should be unique.
        @UniqueConstraint(columnNames = "firstName"), // firstName column should be unique.
        @UniqueConstraint(columnNames = "lastname"), // lastname column should be unique.
        @UniqueConstraint(columnNames = "email") // email column should be unique.
})
@Getter @Setter // Lombok annotations to auto-generate getters and setters.
@AllArgsConstructor // Lombok annotation to generate a constructor with all properties.
@ToString // Lombok annotation to generate a toString method.
@NoArgsConstructor // Lombok annotation to generate a no-args constructor.
@FieldDefaults(level = AccessLevel.PRIVATE) // Lombok annotation to set the access level of class fields, in this case, to private.
public class User implements  UserDetails { // The class implements Serializable and UserDetails, which means it can be converted to a byte stream and restored later, and it can provide core user information.

    @Id // This annotation specifies the primary key of an entity.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This annotation provides for the specification of generation strategies for the values of primary keys.
    Integer id ; // The primary key.

    String firstName ; // The first name of the user.

    String lastName ; // The last name of the user.

    String email; // The email of the user.
    
    String password ; // The password of the user.

    String userRole ; // The role of the user.

    @ManyToMany(fetch = FetchType.EAGER  , cascade = CascadeType.PERSIST) // This annotation defines a many-to-many relationship between the User and Role entities.
    List <Role> roles ; // The roles that the user has.

    public User (String email , String password , List<Role> roles) { // Constructor that sets the email, password, and roles.
      this.email= email ;
      this.password=password ;
      this.roles=roles ;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // This method returns the authorities granted to the user.
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName()))); // Convert each role to a SimpleGrantedAuthority and add it to the authorities list.
        return authorities;
    }

    @Override
    public String getUsername() { // This method returns the username used to authenticate the user.
        return this.email; // The email is used as the username.
    }

    @Override
    public boolean isAccountNonExpired() { // This method checks if the user's account has not expired.
        return true; // The account is always non-expired.
    }

    @Override
    public boolean isAccountNonLocked() { // This method checks if the user's account is not locked.
        return true; // The account is always non-locked.
    }

    @Override
    public boolean isCredentialsNonExpired() { // This method checks if the user's credentials (password) has not expired.
        return true; // The credentials are always non-expired.
    }

    @Override
    public boolean isEnabled() { // This method checks if the user is enabled.
        return true; // The user is always enabled.
    }
}