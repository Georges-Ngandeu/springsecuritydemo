package com.playground.securitydemo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.playground.securitydemo.models.User;
import com.playground.securitydemo.repository.UserRepository;

@Component // This annotation indicates that the class is a component. Such classes are considered as candidates for auto-detection when using annotation-based configuration and classpath scanning.
@RequiredArgsConstructor // Lombok annotation to generate a constructor with required arguments. Required arguments are final fields and fields with constraints such as @NonNull.
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserRepository UserRepository ; // A UserRepository instance for interacting with the database.

    @Override
    // This method loads the user data by the given username (in this case, email).
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // It tries to find a User entity by its email. If it doesn't find any, it throws a UsernameNotFoundException.
        User user = UserRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found !"));
        return  user ; // Returns the found User entity.
    }
}