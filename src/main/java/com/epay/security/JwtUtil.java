package com.epay.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final JwtConfig jwtConfig;
    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(String username) {
        Key key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ jwtConfig.getExpiration()))
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token){
        Key key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return claims.getBody().getSubject();
    }

    public boolean validateToken(String token){
        try {
            Key key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }
}
