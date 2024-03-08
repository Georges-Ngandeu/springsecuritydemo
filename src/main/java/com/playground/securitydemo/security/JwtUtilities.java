package com.playground.securitydemo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Slf4j // Lombok annotation for logging.
@Component // This annotation indicates that the class is a component. Such classes are considered as candidates for auto-detection when using annotation-based configuration and classpath scanning.
public class JwtUtilities {

    @Value("${jwt.jwtsecret}") // This annotation indicates a default value expression for the field or parameter to be injected.
    private String jwtsecret; // The secret key for signing the JWT.

    @Value("${jwt.jwtExpirationTime}") // This annotation indicates a default value expression for the field or parameter to be injected.
    private Long jwtExpirationTime; // The expiration time of the JWT.

    // This method extracts the username (in this case, email) from the JWT.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // This method extracts all claims from the JWT.
    public Claims extractAllClaims(String token) {
        //return Jwts.parser().setSigningKey(jwtsecret).parseClaimsJws(token).getBody();
        return Jwts.parser().verifyWith(Keys.password(jwtsecret.toCharArray())).build().parseSignedClaims(token).getPayload();
    }

    // This method extracts a claim from the JWT.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // This method extracts the expiration date from the JWT.
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // This method validates the JWT.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractUsername(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // This method checks if the JWT has expired.
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // This method generates a JWT for the given email and roles.
    public String generateToken(String email , List<String> roles) {
        return Jwts.builder().setSubject(email).claim("role",roles).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plus(jwtExpirationTime, ChronoUnit.MILLIS)))
                .signWith(SignatureAlgorithm.HS256, jwtsecret).compact();
    }

    // This method validates the JWT and logs any exceptions that occur during validation.
    //public boolean validateToken(String token) {
        // ... (omitted for brevity)
    //}

    // This method extracts the JWT from the Authorization header of the HTTP request.
    public String getToken (HttpServletRequest httpServletRequest) {
        final String bearerToken = httpServletRequest.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7,bearerToken.length()); // The part after "Bearer "
        }
        return null;
    }
}