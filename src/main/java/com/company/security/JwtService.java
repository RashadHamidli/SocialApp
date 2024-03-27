package com.company.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {
    @Value("${my_social_app_secret_key}")
    private String SECRET_KEY;
    @Value("${my_social_app_issuer}")
    private String ISSUER;
    @Value("${my_social_app_expires_generate}")
    private Long EXPIRES_GENERATE;
    @Value("${my_social_app_expires_refresh}")
    private Long EXPIRES_REFRESH;

    public String generateToken(CustomUserDetails userDetails){
        return generateTokenService(userDetails, EXPIRES_GENERATE);
    }
    public String refreshToken(CustomUserDetails userDetails){
        return generateTokenService(userDetails, EXPIRES_REFRESH);
    }
    private String generateTokenService(CustomUserDetails userDetails, Long EXPIRES) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(userDetails.getUsername())
                .withClaim("username", userDetails.getUsername())
                .withClaim("email", userDetails.getEmail())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRES))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public DecodedJWT decodedJWT(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
    }

    public Boolean validToken(String token) {
        try {
            JWT.decode(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }


}
