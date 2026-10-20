package com.bishnu.chatapplication.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final Duration accessTokenExpiration;

    public JwtService(
        @Value("${app.jwt.secret}") String secret,
        @Value("${app.jwt.access-token-expiration}") Duration accessTokenExpiration
    ) {
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTokenExpiration = accessTokenExpiration;
    }

    public String generateAccessToken(String username) {
        Instant now = Instant.now();
        return Jwts.builder()
            .subject(username)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(accessTokenExpiration)))
            .signWith(signingKey)
            .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    public long getAccessTokenExpirationSeconds() {
        return accessTokenExpiration.toSeconds();
    }
}
