package com.bachokhachvani.employeemanagementapp.security;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class SecurityConstants {
    public static final String SECRET_BASE64  = Base64.getEncoder().encodeToString("69c0c876559beb6ea8830983145a1424b9c229b713b7184070d90c8e45e92c1c".getBytes());;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final long TOKEN_EXPIRATION = 86400000;
    public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_BASE64));
}


