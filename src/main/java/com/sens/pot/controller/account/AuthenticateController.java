package com.sens.pot.controller.account;
import java.util.HashMap;

import java.util.Map;

import com.sens.pot.common.jwt.JwtTokenProvider;
import com.sens.pot.common.model.ResponseErrorMessage;
import com.sens.pot.common.model.ResponseOkMessage;
import com.sens.pot.service.account.dto.AuthRequestDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;


/**
 * 로그인 인증처리를 한다.
 */
@RestController
@RequiredArgsConstructor
public class AuthenticateController {
    private final JwtTokenProvider jwtUtils;
    private final AuthenticationManager authenticationManager;

    /**
     * 로그인 인증
     * @param authRequest
     * @return
     * @throws Exception
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequestDto authRequest) throws Exception {
        try {
           // 1. 인증 정보 가져온다.
           final Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

           // 2. 인증정보로 JWT 토큰 발행
           final String token = jwtUtils.generateToken(authentication);
  
           // 3. SecurityContextHolder에 인증정보 등록
            SecurityContextHolder.getContext().setAuthentication(authentication);
  
        //    logger.info("[authentication] {}", authentication);
        //    logger.info("Name : {}",authentication.getName());
        //    logger.info("Authorities : {}",authentication.getAuthorities());

           Map<String,Object> map = new HashMap<>();
           map.put("name", authentication.getName());
           map.put("authentication", authentication.getAuthorities()); 
           map.put("token", token);
           return new ResponseOkMessage(map).build();

        } catch (DisabledException ex){
            return new ResponseErrorMessage(HttpStatus.UNAUTHORIZED, ex.getMessage()).build();
        } catch(BadCredentialsException ex){
            return new ResponseErrorMessage(HttpStatus.UNAUTHORIZED, ex.getMessage()).build();
        }catch (Exception ex) {
            return new ResponseErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()).build();
        }
    }
}
