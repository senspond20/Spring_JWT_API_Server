package com.sens.pot.web.api.auth;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.Service.State;
import com.sens.pot.web.service.auth.dto.NaverOAuthResponse;
import com.sens.pot.web.service.auth.dto.OAuthResponse;
import com.sens.pot.web.service.auth.impl.NaverOAuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NaverOAuthController {
    
    private final NaverOAuthService oAuthService;
    private Logger logger = LoggerFactory.getLogger(NaverOAuthController.class);
    
    
    @GetMapping("/auth/naver")
    public void naverLogin(HttpServletRequest request, HttpServletResponse response){
       try {
			  response.sendRedirect( oAuthService.getRequestLoginUrl());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @GetMapping("/auth/naver/callback")
    @ResponseBody
    public Object requestAccessCallback( @RequestParam(value = "code") String authCode, 
                                         @RequestParam(value = "state") String state){
        
		  ResponseEntity<?> responseEntity = oAuthService.requestAccessToken(authCode, state);
      if(responseEntity.getStatusCode() == HttpStatus.OK){
        NaverOAuthResponse response = new ObjectMapper().convertValue(
                                          responseEntity.getBody(), 
                                          NaverOAuthResponse.class);
         // DB에 response 에 저장

          return response;
      }else{
        // 에러 
          return responseEntity.getBody();
      }
    }


}
