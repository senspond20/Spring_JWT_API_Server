package com.sens.pot.common.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sens.pot.service.account.CustomUserDetailService;

import java.io.IOException;
import java.util.Collection;


@PropertySource("classpath:authentication.properties")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

     @Value("${AUTH.HEADER_STRING}")
     private String HEADER_STRING;

     @Value("${AUTH.TOKEN_PREFIX}")
     private String TOKEN_PREFIX;
     
     @Value("${AUTH.AUTHORITIES_NAME}")
     private String AUTHORITIES_NAME;

     @Autowired
     private JwtTokenProvider jwtUtils;

     @Autowired
     private CustomUserDetailService userDetailService;


     @Override
     protected void doFilterInternal(HttpServletRequest httpServletRequest, 
                                HttpServletResponse httpServletResponse, 
                                FilterChain filterChain) throws ServletException, IOException {
        
        final String authorizationHeader = httpServletRequest.getHeader(HEADER_STRING);
   
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            
            SecurityContext securityContext = SecurityContextHolder.getContext();
            final String authToken = authorizationHeader.replace(TOKEN_PREFIX,"");

            if(jwtUtils.validateToken(authToken) && securityContext.getAuthentication() == null){
                final Claims claims = jwtUtils.extractAllClaims(authToken);
                final String userName = claims.getSubject(); 
                final Collection<? extends GrantedAuthority> authorities = jwtUtils.getRoles(claims);
                
                // UserDetails userDetails = userDetailService.loadUserByUsername(userName);
                // UsernamePasswordAuthenticationToken authenticationToken = 
                //    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                 UsernamePasswordAuthenticationToken authenticationToken = 
                   new UsernamePasswordAuthenticationToken(userName,null, authorities);

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                securityContext.setAuthentication(authenticationToken);
       
               /* new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                 authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                   SecurityContextHolder.getContext().setAuthentication(authentication);*/
                   }
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
       
}
