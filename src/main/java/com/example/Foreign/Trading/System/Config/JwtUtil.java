package com.example.Foreign.Trading.System.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY =
            "foreignTradingSystemSuperSecureJwtSecretKey123456";

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Generate JWT Token
    public String generateToken(String username, String role) {

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Extract role
    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    // Extract all claims
    public Claims extractClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Check token expiration
    public boolean isTokenExpired(String token) {

        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // Validate token
    public boolean validateToken(String token, UserDetails userDetails) {

        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }
}