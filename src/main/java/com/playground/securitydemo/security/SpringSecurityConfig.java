package com.playground.securitydemo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // This annotation declares that this class is a source of bean definitions for the application context.
@EnableWebSecurity // This annotation switches on Spring Security's web security support.
@RequiredArgsConstructor // Lombok annotation to generate a constructor with required arguments. Required arguments are final fields and fields with constraints such as @NonNull.
public class SpringSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter ; // A JwtAuthenticationFilter instance for filtering requests based on JWT.
    private final CustomerUserDetailsService customerUserDetailsService ; // A CustomerUserDetailsService instance for loading user-related data.

    @Bean
    // This method configures the security filter chain.
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception
    { 
        http
            .csrf().disable() // Disables CSRF protection.
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configures session management to be stateless.
            .authorizeHttpRequests(auth ->
                auth.requestMatchers("/public/**", "/user/**").permitAll() // Allows all users to access "/public/**" and "/user/**".
            .requestMatchers("/admin/**").hasAuthority("ADMIN")) ; // Only allows users with ADMIN authority to access "/admin/**".

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Adds the JwtAuthenticationFilter before the UsernamePasswordAuthenticationFilter.

        return  http.build(); // Builds and returns the security filter chain.
    }

    @Bean
    // This method returns the AuthenticationManager from the AuthenticationConfiguration.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    { return authenticationConfiguration.getAuthenticationManager();}

    @Bean
    // This method returns a new BCryptPasswordEncoder.
    public PasswordEncoder passwordEncoder()
    { return new BCryptPasswordEncoder(); }

}