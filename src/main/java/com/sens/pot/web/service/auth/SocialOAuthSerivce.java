package com.sens.pot.web.service.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SocialOathService 
 * 구글,네이버,카카오
 */
public interface SocialOAuthSerivce {
    /**
     * Social OAuth Login Page로 요청
     * @param request
     */
    void requestLogin(HttpServletRequest request, HttpServletResponse response);

    /**
     * Login Page 로부터 로그인 인증시 AccessTok
     * @param code
     * @return
     */
    String requestAccessToken(String code);
 
  
}
