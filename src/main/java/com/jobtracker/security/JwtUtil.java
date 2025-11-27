package com.jobtracker.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    
    @Value("${jwt.secret:mySecretKeyForJobTrackerApplication2024SecureAndLongEnough}")
    private String secret;
    
    @Value("${jwt.expiration:86400000}")
    private Long expiration;
    
    private SecretKey getSigningKey() {
        try {
            // Ensure the secret is at least 32 characters for HS256
            if (secret == null || secret.length() < 32) {
                log.warn("JWT secret is too short, using default strengthened secret");
                secret = "mySecretKeyForJobTrackerApplication2024SecureAndLongEnough";
            }
            byte[] keyBytes = secret.getBytes();
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            log.error("Error creating signing key: {}", e.getMessage());
            throw new RuntimeException("JWT configuration error", e);
        }
    }
    
    public String generateToken(String username) {
        try {
            log.info("Generating token for username: {}", username);
            
            String token = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
            
            log.info("Token generated successfully for user: {}", username);
            return token;
        } catch (Exception e) {
            log.error("Error generating token for user {}: {}", username, e.getMessage());
            throw new RuntimeException("Token generation failed", e);
        }
    }
    
    public String extractUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            log.error("Error extracting username from token: {}", e.getMessage());
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
    
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            boolean isValid = username.equals(userDetails.getUsername()) && !isTokenExpired(token);
            log.info("Token validation for user {}: {}", username, isValid);
            return isValid;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }
    
    private boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            log.error("Error checking token expiration: {}", e.getMessage());
            return true;
        }
    }
}