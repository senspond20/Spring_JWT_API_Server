package com.sens.pot.web.service.social.impl;

import com.sens.pot.web.service.social.SocailOauthValidService;

public class FacebookOauthValidService implements SocailOauthValidService {

    @Override
    public String getOauthRedirectURL() {
        return "null";
    }

    @Override
    public String requestAccessToken(String code) {
        return null;
    }

}
