package com.sens.pot.common.jwt;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
@PropertySource("classpath:authentication.properties")
public class JwtTokenProvider implements Serializable {
 
    /**
     *
     */
    private static final long serialVersionUID = -6129061733252923754L;

    private Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    
    @Value("${AUTH.AUTHORITIES_NAME}")
    private String AUTHORITIES_NAME;

    @Value("${AUTH.TOKEN_VALIDITY}")
    private Long TOKEN_VALIDITY;

    @Value("${AUTH.SIGNING_KEY}")
    private String SIGNING_KEY;



    
    /**
     * 토큰은 검증한다.
     * @param token
     * @return
     */
    public boolean validateToken(String token){
        if (!token.isEmpty()) {
            try {
                Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token);
                return true;
            

            } catch (SignatureException e) {
                logger.error("============== Invalid JWT signature ==============");
            } catch (MalformedJwtException e) {
                logger.error("============== Invalid JWT token ==============");
            } catch (ExpiredJwtException e) {
                logger.error("============== Expired JWT token =============="); // 기간 만료
            } catch (UnsupportedJwtException e) {
                logger.error("============== Unsupported JWT token ==============");
            } catch (IllegalArgumentException e) {
               logger.error("============== JWT claims string is empty ==============");
            }
        }
        return false;
    }


    /**
     * 토큰값 해독하여 추출한다.
     * @param token
     * @return
     */
    protected Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();
    }
    public String getUser(Claims claims){
        return claims.getSubject();
    }
    protected Collection<? extends GrantedAuthority> getRoles(Claims claims){
        final Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_NAME).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
     }
    public Date extractExpiration(String token) {
          return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
         final Claims claims = extractAllClaims(token);
         return claimsResolver.apply(claims);

    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    /**
     * 토큰을 생성한다. (claims에 권한을 추가하며)
     * @param authentication
     * @return
     */
    public String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        String authorities = authentication.getAuthorities().stream()
                                                            .map(GrantedAuthority::getAuthority)
                                                            .collect(Collectors.joining(","));

        claims.put(AUTHORITIES_NAME, authorities);
        return createToken(claims, authentication.getName());
    }

    /**
     * @apiNote 토큰을 생성한다.
     * @param   claims
     * @param   subject
     * @return
     */
    private String createToken(Map<String, Object> claims, String subject) {
  //      Map<String, Object> headers = new HashMap<>();
  //      headers.put("typ", "JWT");
  //      headers.put("alg", "HS256");

        return Jwts.builder()
//                   .setHeader(headers) // Headers 설정
                   .setClaims(claims)   // payloads 설정
                   .setSubject(subject)  //
                   // 토큰 유효 기간 설정
                   .setIssuedAt(new Date(System.currentTimeMillis())) 
                   .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                    // signature
                   .signWith(SignatureAlgorithm.HS256, SIGNING_KEY) 
                   .compact(); // 생성
    }

  
    // public Boolean validateToken(String token, UserDetails userDetails) {
    //     final String username = extractUsername(token);
    //      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    // }
    // public Boolean validateToken(String token, String name) {
    //     final String username = extractUsername(token);
    //      return (username.equals(name) && !isTokenExpired(token));
    // }
    
}