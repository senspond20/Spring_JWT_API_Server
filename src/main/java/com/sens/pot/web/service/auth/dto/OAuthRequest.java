package com.sens.pot.web.service.auth.dto;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Oauth 스네이크 케이스로 파라미터 일치 수정.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OAuthRequest {
	private String redirect_uri;
	private String client_id;
	private String client_secret;
	private String code;
	private String response_type;
	private String scope;
	private String grant_type;
	private String state;

	/**
	 * null 파라미터를 제외한 MultiValueMap 으로 변환
	 * @return
	 */
	public MultiValueMap<String, String> getMultiValueMapFromNonNull(){
        ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		mapper.setSerializationInclusion(Include.NON_NULL);
        MultiValueMap<String, String> resultMap = new LinkedMultiValueMap<>();
        Map<String, String> map = mapper.convertValue(this, new TypeReference<Map<String, String>>() {}); 
        resultMap.setAll(map);
		System.out.println(map);
		return resultMap;
	}

}