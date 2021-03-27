package com.sens.pot.web.service.auth.dto;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoogleOAuthRequest {
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


	public String getRequestLoginUrl(String baseUrl){
		return UriComponentsBuilder.fromHttpUrl(baseUrl)
								   .queryParam("scope", this.scope)
								   .queryParam("state", this.state)
								   .queryParam("response_type", this.responseType)
								   .queryParam("client_id", this.clientId)
								   .queryParam("redirect_uri", this.redirectUri)
								   .encode()
								   .toUriString();
	}

}