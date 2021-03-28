package com.sens.pot.service.social;


import java.math.BigInteger;
import java.security.SecureRandom;

import java.util.Map;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class GoogleOAuthService implements SocialOAuthSerivce{
 
    private String GOOGLE_CLIENT_ID ="304210542468-tg4qh5fqdb0fbbkp51tl96ap21mspaaq.apps.googleusercontent.com";
    private String GOOGLE_CLIENT_SECRET ="ee0lblcbpYkO-Aq9oVUAUcwV";
    private String GOOGLE_TOKEN_BASE_URL ="https://oauth2.googleapis.com/token";
    private String GOOGLE_CALLBACK_URL ="http://localhost:8083/auth/google/callback";
    private String GOOGLE_AUTH_BASE_URL ="https://accounts.google.com/o/oauth2/v2/auth";

      /**
     * 네아로 url Get
     * @return
     */
    public String getRequestLoginUrl() {
        final String state = new BigInteger(130, new SecureRandom()).toString();

        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
            requestParam.add("response_type", "code");
            requestParam.add("scope", "https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/plus.me+https://www.googleapis.com/auth/userinfo.profile");
            requestParam.add("state", state);
            requestParam.add("client_id", GOOGLE_CLIENT_ID);
            requestParam.add("redirect_uri", GOOGLE_CALLBACK_URL);

        return UriComponentsBuilder.fromUriString(GOOGLE_AUTH_BASE_URL)
                .queryParams(requestParam)
                .build().encode()
                .toString();
    }

    /**
     * 구글 콜백 - AccessToken 
     * @param code
     * @param state
     * @return
     */
    public ResponseEntity<?> requestAccessToken(String code, String state) {

         MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("code", code);
            requestBody.add("state", state);
            requestBody.add("client_id", GOOGLE_CLIENT_ID);
            requestBody.add("client_secret", GOOGLE_CLIENT_SECRET);
            requestBody.add("grant_type", "authorization_code");

        return new RestTemplate().postForEntity(GOOGLE_TOKEN_BASE_URL, requestBody, Map.class);
    }

	/**
	 * IdToken 값으로부터 사용자 정보 get
	 * @return
	 */
	public Map<String,String> getUserInfo(String idToken){

		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE); // 스네이크 케이스
		mapper.setSerializationInclusion(Include.NON_NULL);

		String requestUrl = UriComponentsBuilder
			.fromHttpUrl("https://oauth2.googleapis.com/tokeninfo")
			.queryParam("id_token", idToken).encode()
			.toUriString();

		String resultJson = new RestTemplate().getForObject(requestUrl, String.class);
		Map<String,String> userInfo = null;
		try {
			userInfo = mapper.readValue(resultJson, new TypeReference<Map<String, String>>(){});
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        return userInfo;
	}

    @Override
    public void revokeToken(String accessToken) {
        // TODO Auto-generated method stub
        
    }


    public void getUserInfo2(String response){
        //HTTP Request를 위한 RestTemplate
        // RestTemplate restTemplate = new RestTemplate();

        // //Google OAuth Access Token 요청을 위한 파라미터 세팅
        // GoogleOAuthRequestDto googleOAuthRequestParam = GoogleOAuthRequestDto
        //     .builder()
        //     .clientId(GOOGLE_CLIENT_ID)
        //     .clientSecret(GOOGLE_CLIENT_SECRET)
        //     .code(authCode)
        //     .redirectUri(GOOGLE_CALLBACK_URL)
        //     .grantType("authorization_code").build();


        // //JSON 파싱을 위한 기본값 세팅
        // //요청시 파라미터는 스네이크 케이스로 세팅되므로 Object mapper에 미리 설정해준다.
        // ObjectMapper mapper = new ObjectMapper();
        // mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        // mapper.setSerializationInclusion(Include.NON_NULL);

        // //AccessToken 발급 요청
        // ResponseEntity<String> resultEntity = restTemplate.postForEntity(GOOGLE_TOKEN_BASE_URL, googleOAuthRequestParam, String.class);

        // //Token Request
        // GoogleOAuthResponse result = mapper.readValue(resultEntity.getBody(), 
        //     new TypeReference<GoogleOAuthResponse>() {
        // });

        // //ID Token만 추출 (사용자의 정보는 jwt로 인코딩 되어있다)
        // String jwtToken = result.getIdToken();
        // String requestUrl = UriComponentsBuilder
        // .fromHttpUrl("https://oauth2.googleapis.com/tokeninfo")
        // .queryParam("id_token", jwtToken).encode().toUriString();

        // String resultJson = restTemplate.getForObject(requestUrl, String.class);
        // Map<String,String> userInfo = mapper.readValue(resultJson, new TypeReference<Map<String, String>>(){});
        
        // model.addAllAttributes(userInfo);
        // model.addAttribute("token", result.getAccessToken());

    }


   
}
