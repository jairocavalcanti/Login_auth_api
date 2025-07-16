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
    // The "secret" string variable above holds the value assigned from "${api.security.token.secret}".
    @Value("${api.security.token.secret}")
    private String secret;
    
    // Metodo String destinado á criação do token referente ao usuario passado como argumento
    // String method that creates a token for the user passed in the arguments.
    public String generateToken(User user) {
        try {
            // Cria o algoritmo de assinatura HMAC com a chave secreta
            // Creating signed algorithm HMAC with secret key 
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Criando token JWT 
            // Creating JWT token
            String token = JWT.create()
                .withIssuer("login-auth-api")
                .withSubject(user.getEmail())
                .withExpiresAt(this.generateExpirationDate())
                .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while authenticating");
        }
    }

    public String validateToken(String token){
       try {
        Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                   .withIssuer("login auth api")
                   .build()
                   .verify(token)
                   .getSubject();
       } catch (JWTCreationException e) {
          return null;
       } 
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now()
        .plusHours(2)
        .toInstant(ZoneOffset.of("-3"));
    }
}
