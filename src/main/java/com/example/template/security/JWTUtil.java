package com.example.template.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.template.models.Role;
import com.example.template.models.User;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    public static final long TOKEN_VALIDITY = 24 * 60 * 60;

    private Logger logger = Logger.getLogger(JWTUtil.class.getName());

    public boolean isTokenValid(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return decodedJWT.getExpiresAt().after(new Date());
        } 
        catch (Exception e) {
            logger.warning(e.getMessage());
        }

        return false;
    }

    public String generateToken(User user) {
        Role role = user.getRoles().iterator().next();
        return JWT.create().withSubject(user.getEmail())
            .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
            .withClaim("role", role.getName()).sign(Algorithm.HMAC256(this.secret));
    }

    public String getUsername(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        return jwtVerifier.verify(token).getSubject();
    }
}
