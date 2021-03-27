package com.sens.pot.web.service.auth.dto;

import lombok.Getter;
import lombok.ToString;

/**
 * 구글 응답
 */
@Getter
@ToString
public class GoogleOAuthResponse extends OAuthResponse{
	private String id_token;
	private String scope;
}