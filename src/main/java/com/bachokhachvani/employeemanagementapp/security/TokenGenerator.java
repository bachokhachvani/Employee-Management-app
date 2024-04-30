package com.bachokhachvani.employeemanagementapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TokenGenerator {
    public String generateToken(Authentication auth) {
        String username = auth.getName();
        Date now = new Date();
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Date expiryDate = new Date(now.getTime() + SecurityConstants.TOKEN_EXPIRATION);
        return Jwts.builder().subject(username).claim("authorities", authorities).expiration(expiryDate).issuedAt(now).signWith(SecurityConstants.SECRET_KEY).compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SecurityConstants.SECRET_KEY)
                .build()
                .parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(SecurityConstants.SECRET_KEY).build().parseSignedClaims(token).getPayload();
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}
