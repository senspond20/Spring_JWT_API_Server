package com.sens.pot.service.social;

/**
 * SocialOathService 
 * 구글,네이버,카카오
 */
public interface SocialOAuthSerivce {
    /**
     * 1. Social 로그인 폼 Url Get
     * @param request
     * @return String
     */
    String getRequestLoginUrl();

    /**
     * 2. 로그인 인증시 인증서버에서 응답가져옴 (AccessToken )
     * @param code
     * @return Object
     */
    Object requestAccessToken(String code, String state);

    /**
     * 3. 토큰갱신 . 리프레쉬 토큰 발급 정의해야함
     */
    

    /**
     * 4. 발급받은 토큰 무효화 
     * @param accessToken
     */
    void revokeToken(String accessToken);
}
