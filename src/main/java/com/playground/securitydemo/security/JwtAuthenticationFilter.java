package com.playground.securitydemo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j // Lombok annotation for logging.
@Component // This annotation indicates that the class is a component. Such classes are considered as candidates for auto-detection when using annotation-based configuration and classpath scanning.
@RequiredArgsConstructor // Lombok annotation to generate a constructor with required arguments. Required arguments are final fields and fields with constraints such as @NonNull.
public class JwtAuthenticationFilter extends OncePerRequestFilter {

   private final JwtUtilities jwtUtilities ; // A JwtUtilities instance for working with JSON Web Tokens (JWT).
   
   private final CustomerUserDetailsService customerUserDetailsService ; // A CustomerUserDetailsService instance for loading user-related data.

    @Override
    // This method is executed once per request. It checks if the request has a valid JWT and sets the authentication in the context.
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, // The HTTP request.
        @NonNull HttpServletResponse response, // The HTTP response.
        @NonNull FilterChain filterChain) // The filter chain.
        throws ServletException, IOException {

        String token = jwtUtilities.getToken(request) ; // Extracts the JWT from the request.

        if (token!=null) // If there is a JWT,
        {
            String email = jwtUtilities.extractUsername(token); // Extracts the username (email) from the JWT.

            UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email); // Loads the user data by the email.
            if (userDetails != null) { // If the user data is not null,
                // Creates an authentication token with the username, null credentials, and the authorities of the user.
                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername() ,null , userDetails.getAuthorities());
                log.info("authenticated user with email :{}", email); // Logs the authenticated user's email.
                SecurityContextHolder.getContext().setAuthentication(authentication); // Sets the authentication in the context.

            }
        }
        
        filterChain.doFilter(request, response); // Continues the filter chain.
    }

}