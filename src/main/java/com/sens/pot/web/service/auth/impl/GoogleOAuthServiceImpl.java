package com.sens.pot.web.service.auth.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sens.pot.web.service.auth.SocialOAuthSerivce;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleOAuthServiceImpl implements SocialOAuthSerivce{
 
    private String GOOGLE_SNS_BASE_URL ="https://accounts.google.com/o/oauth2/v2/auth";
    private String GOOGLE_SNS_CLIENT_ID ="304210542468-tg4qh5fqdb0fbbkp51tl96ap21mspaaq.apps.googleusercontent.com";
    private String GOOGLE_SNS_CLIENT_SECRET ="ee0lblcbpYkO-Aq9oVUAUcwV";
    private String GOOGLE_SNS_TOKEN_BASE_URL ="https://oauth2.googleapis.com/token";
    private String GOOGLE_SNS_CALLBACK_URL ="http://localhost:8083/auth/google/callback";

    @Override
    public void requestLogin(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<>();
        params.put("scope", "profile");
        params.put("response_type", "code");
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        final String sendUrl = GOOGLE_SNS_BASE_URL + "?" + parameterString;
        try {
            response.sendRedirect(sendUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }
        return "구글 로그인 요청 처리 실패";
    }


   
}
