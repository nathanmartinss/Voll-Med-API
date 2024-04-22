package com.example.demo.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {

        try {
            var algoritimo = Algorithm.HMAC256(secret); // Cria um algoritimo de criptografia
            return JWT.create().withIssuer("API nathanAPI").withSubject(usuario.getLogin()).withExpiresAt(dataExpiracao()).sign(algoritimo); // Cria um token JWT
        } catch (JWTCreationException exception) { // Caso ocorra um erro ao gerar o token, uma exceção é lançada
            throw new RuntimeException("Erro ao gerar token jwt", exception); // Caso ocorra um erro ao gerar o token, uma exceção é lançada
        }
    }

    public String getSubject (String tokenJWT) {
        try {
            var algoritimo = Algorithm.HMAC256(secret);
            return JWT.require(algoritimo)
                    .withIssuer("API nathanAPI")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private Instant dataExpiracao() { // Método que retorna a data de expiração do token
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); // A data de expiração é de 2 horas após a data atual
    }
}
