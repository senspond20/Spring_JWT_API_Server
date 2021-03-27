package com.sens.pot.web.service.auth.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.sens.pot.web.service.auth.SocialOAuthSerivce;
import com.sens.pot.web.service.auth.dto.NaverOAuthResponse;
import com.sens.pot.web.service.auth.dto.OAuthRequest;
import com.sens.pot.web.service.auth.dto.OAuthResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NaverOAuthService {

    private final String NAVER_CLIENT_ID ="P1euyg28hFvQ4oNU7Jge";
    private final String NAVER_CLIENT_SECRET ="NJ5VCH4TwE";
    private final String NAVER_BASE_URL ="https://nid.naver.com/oauth2.0/authorize?grant_type=authorization_code";
    private final String NAVER_TOKEN_BASE_URL ="https://nid.naver.com/oauth2.0/token";
    private final String NAVER_CALLBACK_URL ="http://localhost:8083/auth/naver/callback";

    /**
     * 네아로 url Get
     * @return
     */
    public String getRequestLoginUrl() {
        final String state = new BigInteger(130, new SecureRandom()).toString();

        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
            requestParam.add("response_type", "code");
            requestParam.add("state", state);
            requestParam.add("client_id", NAVER_CLIENT_ID);
            requestParam.add("redirect_uri", NAVER_CALLBACK_URL);

        return UriComponentsBuilder.fromUriString(NAVER_BASE_URL)
                .queryParams(requestParam)
                .build().encode()
                .toString();
    }

    /**
     * 네아로 콜백 - AccessToken 
     * @param code
     * @param state
     * @return
     */
    public ResponseEntity<?> requestAccessToken(String code, String state) {

         MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("code", code);
            requestBody.add("state", state);
            requestBody.add("client_id", NAVER_CLIENT_ID);
            requestBody.add("client_secret", NAVER_CLIENT_SECRET);
            requestBody.add("grant_type", "authorization_code");

        return new RestTemplate().postForEntity(NAVER_TOKEN_BASE_URL, requestBody, Map.class);
    }


}
