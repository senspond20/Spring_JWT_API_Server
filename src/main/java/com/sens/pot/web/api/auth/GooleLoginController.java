package com.sens.pot.web.api.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sens.pot.web.service.auth.SocialOAuthSerivce;
import com.sens.pot.web.service.auth.impl.GoogleOAuthServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GooleLoginController {
    private final GoogleOAuthServiceImpl oauthService;

    public void etset(HttpServletRequest request, HttpServletResponse response){
        oauthService.requestLogin(request, response);
        //oauthService.requestLogin(request, response);
    }
}
