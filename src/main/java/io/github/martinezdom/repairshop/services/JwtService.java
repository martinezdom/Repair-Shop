package io.github.martinezdom.repairshop.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import io.github.martinezdom.repairshop.entities.User;

@Service
public class JwtService {
    @Value("${api.security.jwt.secret}")
    private String secretKey;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
                .withIssuer("RepairShop")
                .withSubject(user.getEmail())
                .withClaim("role", user.getRole())
                .withExpiresAt(Instant.now().plusSeconds(7200))
                .sign(algorithm);
        return token;
    }
}
