package com.sens.pot.service.social;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sens.pot.common.utils.HttpRequestUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
;

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
    public Object requestUserInfo(String accessToken){

        
        // org.springframework.http.HttpHeaders
        /*
        HttpHeaders headers = new HttpHeaders();
        String base64ClientCredentials = new String(Base64.encodeBase64(accessToken.getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON); 
        headers.add("Authorization", "Bearer " + base64ClientCredentials); 
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<HttpHeaders>(headers);
        return new RestTemplate().exchange(NAVER_PROFILE_URL, HttpMethod.POST, requestEntity, Map.class).getBody();
        //    return new RestTemplate().postForEntity(NAVER_PROFILE_URL, request, Map.class).getBody();
        */
        // HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);

        // 순수 자바코드로 유틸만듬 (스프링 의존 X)
        Map<String,String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization","Bearer " + accessToken);
        String responseJson = HttpRequestUtils.post(NAVER_PROFILE_URL, requestHeaders);
        System.out.println(responseJson);
        // Map<String,String> map = null;
        // try {
        //     map = new ObjectMapper().readValue(responseJson , new TypeReference<LinkedHashMap<String, String>>(){});
        // } catch (JsonProcessingException e) {
        //     e.printStackTrace();
        // }
        Map<String,String> map = new LinkedHashMap<String,String>();

        // "[\"][^\\,\\{\\}]*" 패턴으로 매칭을 한다.
        Pattern pattern = Pattern.compile("[\"][^\\,\\{\\}]*"); 
        Matcher matcher = pattern.matcher(responseJson);

        while(matcher.find()){
             //" : " 을 뽑은 다음
             // "를 없애고 : 를 구분자로 나눈다.
              String[] mk = matcher.group().replaceAll("\"", "").split("\\:"); 
            
          // key, value가 공백이 아닌 값만 취한다.
          String k = mk[0].trim();
          String v = mk[1].trim();
           if(!k.equals("") && !v.equals(""))
             map.put(k, v);
        }
        return map;
        // return responseJson;    
    }


}
