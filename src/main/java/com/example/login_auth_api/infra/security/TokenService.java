package com.example.login_auth_api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.login_auth_api.domainuser.User;

@Service
public class TokenService {

    // "${api.security.token.secret}" in application.properties
    // The "secret" string variable above holds the value assigned from
    // "${api.security.token.secret}".
    @Value("${api.security.token.secret}")
    private String secret;

    // String method that creates a token for the user passed in the arguments.
    public String generateToken(User user) {
        try {

            // Creating signed algorithm HMAC with secret key
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Creating JWT token
            String token = JWT.create()
                    // token issuer identification
                    .withIssuer("login-auth-api")
                    // token unique identifier
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.generateExpirationDate())
                    // finally apply the digital signature with the secret key and algorithm
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while authenticating");
        }
    }

    // Validating token
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // token verification
            return JWT.require(algorithm)
                    .withIssuer("login auth api")
                    .build()
                    .verify(token)
                    .getSubject();
            // JWT exception for token
        } catch (JWTCreationException exception) {
            return null;
        }
    }

    // private method made to generate an expiration date to token 
    private Instant generateExpirationDate() {
        // LocalDateTime.now: returns the actual local date and time 
        return LocalDateTime.now()
                 // plusHours: adding 2 hours to expirationDate
                .plusHours(2)
                // toInstant: chosing the zone of "local" date and time
                .toInstant(ZoneOffset.of("-3"));
    }
}
