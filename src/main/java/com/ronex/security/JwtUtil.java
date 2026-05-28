package com.ronex.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 🔐 MOVE THIS TO ENV IN PRODUCTION
    private final String SECRET = System.getenv().getOrDefault(
            "JWT_SECRET",
            "default_dev_secret_key_change_me_please_123456789"
    );

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    /* ---------------- GENERATE TOKEN ---------------- */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /* ---------------- EXTRACT USER ---------------- */
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    /* ---------------- VALIDATE TOKEN ---------------- */
    public boolean validateToken(String token, String email) {
        return email.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    /* ---------------- EXPIRY CHECK ---------------- */
    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    /* ---------------- CLAIMS PARSER ---------------- */
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}