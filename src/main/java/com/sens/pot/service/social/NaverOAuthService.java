package com.sens.pot.service.social;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
    private final String NAVER_PROFILE_URL="https://openapi.naver.com/v1/nid/me";

    /**
     * @apiNote 네이버 로그인 페이지 url 을 가져온다
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
     * @apiNote    사용자 정보제공 동의와 네이버 로그인 인증을 거쳐 콜백으로 AccessToken 을 가져온다
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

    /**
     * @apiNote   accessToken으로 사용자 정보를 요청한다.
     * @param token (네이버 : accessToken, 구글 idToken)
     * @return
     */
    public ResponseEntity<?> requestUserInfo(String accessToken){

          // create headers
        // org.springframework.http.HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + accessToken);

        // request를 만든다.
         // create request (MultiValueMap을 이용해도 된다)
        HttpEntity<HttpHeaders> request = new HttpEntity<HttpHeaders>(headers);

        // make request
//        return new RestTemplate().exchange(NAVER_PROFILE_URL, HttpMethod.POST, request, Map.class);
        return new RestTemplate().postForEntity(NAVER_TOKEN_BASE_URL, request, Map.class);
    }


}
