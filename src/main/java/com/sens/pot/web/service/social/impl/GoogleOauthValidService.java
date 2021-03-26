package com.sens.pot.web.service.social.impl;

import java.util.stream.Collectors;

import com.sens.pot.web.service.social.SocailOauthValidService;
import com.sens.pot.web.service.social.dto.GoogleOAuthRequestDto;
import com.sens.pot.web.service.social.dto.GoogleOAuthRequestDto.GoogleOAuthRequestDtoBuilder;

public class GoogleOauthValidService implements SocailOauthValidService{

    @Override
    public String getOauthRedirectURL() {
        GoogleOAuthRequestDtoBuilder dto = GoogleOAuthRequestDto.builder();
        dto.scope("profile");
        dto.responseType("code");
        dto.clientId("clientId");
        dto.redirectUri("redirectUri");
        dto.build();
        return null;
    }

    @Override
    public String requestAccessToken(String code) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
