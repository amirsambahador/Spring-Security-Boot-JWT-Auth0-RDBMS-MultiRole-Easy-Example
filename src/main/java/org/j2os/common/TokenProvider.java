package org.j2os.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
    Bahador, Amirsam
 */
@Component
@Slf4j
public class TokenProvider {
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.issuer}")
    private String issuer;

    public String getToken(String username) throws IllegalArgumentException, JWTCreationException {
        log.info("getToken");
        return JWT.create()
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String verifyToken(String token) throws JWTVerificationException {
        log.info("verifyToken");
        return JWT.require(Algorithm.HMAC256(secretKey))
                .withIssuer(issuer)
                .build().verify(token).getClaim("username").asString();
    }
}
