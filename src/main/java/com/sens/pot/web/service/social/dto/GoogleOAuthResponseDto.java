package com.sens.pot.web.service.social.dto;

import lombok.Data;

@Data
public class GoogleOAuthResponseDto {
    private String accessToken;
	private String expiresIn;
	private String refreshToken;
	private String scope;
	private String tokenType;
	private String idToken;
}
