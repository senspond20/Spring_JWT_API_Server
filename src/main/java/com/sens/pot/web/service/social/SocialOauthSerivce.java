package com.sens.pot.web.service.social;

import com.sens.pot.common.helper.constants.SocialLoginType;

/**
 * SocialOathService 
 * socialLoginType : 구글,네이버,카카오
 */
public interface SocialOauthSerivce {

    void request(SocialLoginType socialLoginType);

    String requestAccessToken(SocialLoginType socialLoginType, String code);

    SocailOauthValidService findSocialOauthByType(SocialLoginType socialLoginType);
}
