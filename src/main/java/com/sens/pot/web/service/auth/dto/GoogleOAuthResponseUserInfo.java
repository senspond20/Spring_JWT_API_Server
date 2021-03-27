package com.sens.pot.web.service.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoogleOAuthResponseUserInfo {
    private String iss;
    private String azp;
    private String aud;
    private String sub;
    private String email;
    private String email_verified;
    private String at_hashl;
    private String name;
    private String picture;
    private String given_name;
    private String family_name;
    private String localel;
    private String iat;
    private String exp;
    private String alg;
    private String kid;
    private String typ;
}
