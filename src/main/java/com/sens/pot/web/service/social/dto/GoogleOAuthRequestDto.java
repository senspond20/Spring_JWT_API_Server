package com.sens.pot.web.service.social.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(of ="clientId")
@Getter
@Builder
@ToString
public class GoogleOAuthRequestDto {
    private String redirectUri;
	private String clientId;
	private String clientSecret;
	private String code;
	private String responseType;
	private String scope;
	private String accessType;
	private String grantType;
	private String state;
	private String includeGrantedScopes;
	private String loginHint;
	private String prompt;
}
