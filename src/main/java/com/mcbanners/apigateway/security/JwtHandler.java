package com.mcbanners.apigateway.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtHandler {
    private JwtParser parser;

    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${security.jwt.secret:secret}")
    private String secret;

    @PostConstruct
    protected void postConstruct() {
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.parser = Jwts.parser().verifyWith(secretKey).build();
    }

    public void parse(String token) throws JwtException {
        parser.parseClaimsJws(token).getBody();
    }

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }
}
