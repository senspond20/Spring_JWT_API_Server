package com.sens.pot.controller.social;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sens.pot.service.social.NaverOAuthService;

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

      Object responseMessage = responseEntity.getBody();

      if(responseEntity.getStatusCode() == HttpStatus.OK){
          return responseMessage;
      }else{
        // 에러 
          return responseMessage;
      }
    }

    @GetMapping("/auth/naver/userinfo")
    public Object requestUserInfo(@RequestParam(value = "accessToken") String accessToken){
        return oAuthService.requestUserInfo(accessToken);
    }

}
