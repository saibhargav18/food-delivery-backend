package com.fooddelivery.food_delivery_backend.util;
import org.springframework.beans.factory.annotation.Value;  // ✅ Spring's @Value


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import static io.jsonwebtoken.Jwts.*;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;
    // ✅ Lazy initialization - key created when first needed
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username,long expiryMinutes){
        return builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiryMinutes * 60 * 1000))
                .signWith(getSigningKey())
                .compact();

    }

    //validate token
    public String validateAndExtractUserName(String token){

        try {
           return Jwts.parserBuilder()
                   .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

        }
        catch (JwtException e){
            return null;
        }

    }


}