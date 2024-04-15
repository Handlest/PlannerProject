package com.example.plannerapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.plannerapi.domain.entities.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.time.Instant.ofEpochSecond;

@Service
public class JwtService {
    @Value("${token.signing.key}")
    private String jwtSigningKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long jwtRefreshExpiration;

    public String generateAccessToken(UserDetails userDetails){
        return generateAnyToken(userDetails, jwtExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateAnyToken(userDetails, jwtRefreshExpiration);
    }

    private String generateAnyToken(UserDetails userDetails, long jwtExpiration) {
        HashMap<String, Object> claims = new HashMap<>();
        Date creationDate = new Date();
        if (userDetails instanceof UserEntity customUserDetails) {
            claims.put("id", customUserDetails.getUserId());
            claims.put("email", customUserDetails.getEmail());
            claims.put("username", customUserDetails.getUsername());
            claims.put("role", customUserDetails.getRole().name());
            claims.put("createdAt", creationDate);
            claims.put("expires", creationDate.toInstant().plus(jwtExpiration, ChronoUnit.SECONDS));
        }
        return prepareToken(claims);
    }

    private String prepareToken(HashMap<String, Object> claims) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSigningKey);
            return JWT.create().withPayload(claims).sign(algorithm);
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public Map<String, Claim> getTokenPayload(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSigningKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public String extractUsername(String token){
        return getTokenPayload(token).get("username").asString();
    }

    private boolean isTokenExpired(String token) {
        Instant expiration = getTokenPayload(token).get("expires").asInstant();
        return expiration.isBefore(new Date().toInstant());
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = getTokenPayload(token).get("username").asString();
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
