package com.sens.pot.Jwt;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Function;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import antlr.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenTest {

    private final int TOKEN_VALIDITY = 60; // 유효시간 5시간
    private final String SIGNING_KEY = "senshig";

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(subject)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                   .signWith(SignatureAlgorithm.HS256, SIGNING_KEY).compact();
    }

    private <T> T extractClaim(Claims claims, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(claims);
    }

    
    @Test
    public void test(){
        String token = null;
        System.out.println("============ Get Token ==========");
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", "admin");
        token = createToken(claims,"senspond");
        System.out.println(token);
        checkToken(token);
    }

    public void checkToken(String token){
        System.out.println("============ From Token ==========");
        Claims claims = Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();
        
        String userName = extractClaim(claims, Claims::getSubject );
        Date date = extractClaim(claims, Claims::getExpiration );

        System.out.println(claims);
        System.out.println(userName);
        System.out.println(date);
    }

    @Test
    public void test2(){
        checkToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZW5zcG9uZCIsInJvbGVzIjoiYWRtaW4iLCJleHAiOjE2MTYzMDg5OTUsImlhdCI6MTYxNjMwODkzNX0.G_kYO99foGpTJHsjFgBA69Q-tP24d5Z7lXU25s9uVQI");
    }
    

}