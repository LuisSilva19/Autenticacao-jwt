package com.security.board.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.security.board.exception.CustomException;
import com.security.board.exception.ExceptionCodes;
import com.security.board.model.AuthResponse;
import com.security.board.model.request.LoginAuthRequest;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.subscriptionKey}")
    private String subscriptionKey;

    public String generateToken(LoginAuthRequest loginAuthRequest) {
        Algorithm algorithm = Algorithm.HMAC256(subscriptionKey.getBytes());
        return JWT.create()
                .withSubject(loginAuthRequest.getEmail())
                .withClaim("role", loginAuthRequest.getAdmin())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
                .sign(algorithm);
    }

    public boolean validateToken(String token, String email) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(subscriptionKey.getBytes());
            JWT.require(algorithm)
                    .withSubject(email)
                    .build()
                    .verify(token);
            return true;
        } catch (ExpiredJwtException | JWTVerificationException e) {
            throw new CustomException(ExceptionCodes.TOKEN_INVALID);
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(createSecretKey(subscriptionKey))
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        var roleClaim = claims.get("role");

        if (Objects.nonNull(roleClaim) && roleClaim instanceof Boolean && Boolean.parseBoolean(roleClaim.toString())) {
           authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        UserDetails userDetails = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public String extractEmailFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(createSecretKey(subscriptionKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getSubject();

        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(ExceptionCodes.TOKEN_INVALID);
        }
    }

    public AuthResponse refreshToken(String token) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(createSecretKey(subscriptionKey))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return new AuthResponse(Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
                .signWith(createSecretKey(subscriptionKey))
                .compact());
    }

    private Key createSecretKey(String secret) {
        byte[] encodedKey = secret.getBytes();
        return new SecretKeySpec(encodedKey, "HMACSHA256");
    }

}
