package com.example.productioReady.productioReady.services;

import com.example.productioReady.productioReady.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }


    public String generateToken(UserEntity user) {
       return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("role", Set.of("ADMIN", "USER"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60)) // 1 minute expiration
                .signWith(getSecretKey())
                .compact();
    }

    public long getUserIdFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.valueOf(claims.getSubject());
    }
}

// Key elements (brief)
//Package & imports
//Purpose: organizes class and brings in JWT, Spring, crypto and JDK types used for token creation and parsing.
//@Service
//Marks the class as a Spring service bean (component scanned and injectable).
//@Value("${jwt.secretKey}") private String jwtSecretKey;
//Injects the configured secret from application properties/environment. Important for signing and verifying tokens.
//getSecretKey()
//Uses Keys.hmacShaKeyFor(...) to create a SecretKey from the raw bytes of the secret string. Ensures an HMAC key object for signing.
//generateToken(UserEntity user)
//Builds a JWT:
//subject = user.getId().toString() (stable identifier).
//claim("email", ...) = convenience user info in token payload.
//claim("role", Set.of(...)) = attaches roles (watch serialization).
//issuedAt and expiration = token lifetime (here hardcoded to 1 minute).
//signWith(getSecretKey()) = HMAC signing with configured key.
//compact() = final token string.
//getUserIdFromToken(String token)
//Parses the token and extracts claims, returning the subject as a long. Current parsing code may use nonstandard / outdated API calls — see fix below.
//Deeper notes, pitfalls, and interview points
//Secret management
//Ensure jwt.secretKey has sufficient entropy and length (for HS256 at least 256 bits / 32 bytes).
//Prefer a Base64-encoded secret or use asymmetric keys (RS256) for better key rotation practices.
//Never hardcode secrets; use environment variables or a secrets manager.
//Token signing / algorithm
//Keys.hmacShaKeyFor(...) implies HMAC (HS256+/). Know differences between symmetric (HS*) and asymmetric (RS*, ES*) algorithms and tradeoffs.
//Claims design
//Avoid storing sensitive or excessive data in JWT claims.
//Use consistent claim names (e.g., roles as a list of strings) so downstream libraries can parse them reliably.
//Serializing Set.of("ADMIN", "USER") may produce unpredictable JSON ordering; better to use List.of(...).
//Expiration & lifetime
//Hardcoded new Date(System.currentTimeMillis() + 1000 * 60) is short and inflexible. Make expiration configurable and consider refresh token strategy.
//Include iat and exp correctly. Use clock abstraction for easier testing.
//Parsing and validation
//Always validate signature and exp/nbf/iss/aud as needed.
//Handle exceptions from parsing (expired, malformed, signature invalid) and convert them to appropriate 401/403 responses.
//Thread safety & bean design
//Mark fields final and prefer constructor injection for testability.
//SecretKey creation can be cached if the secret does not change.
//Testing
//Unit tests for generate/parse cycle, edge cases (expired, tampered token).
//Integration tests exercising endpoints with and without valid tokens.
//Operational considerations
//Key rotation strategy, revocation (use token blacklist or short-lived tokens), monitoring for auth failures, rate limiting authentication endpoints.
//Common interview questions to prepare
//How does JWT signing differ from encryption?
//HS256 vs RS256: pros and cons.
//What’s the purpose of sub, iat, exp, iss, aud claims?
//Strategies for token revocation and refresh tokens.
//Security concerns when storing JWT on clients (cookies vs localStorage) and CSRF implications.
//How to rotate secrets and maintain backward compatibility.

