package com.sens.pot.web.api.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sens.pot.web.service.auth.dto.GoogleOAuthResponse;
import com.sens.pot.web.service.auth.dto.OAuthResponse;
import com.sens.pot.web.service.auth.impl.GoogleOAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GoogleOAuthController {

    private final GoogleOAuthService oAuthService;
    final static String GOOGLE_REVOKE_TOKEN_BASE_URL = "https://oauth2.googleapis.com/revoke";

	private Logger logger = LoggerFactory.getLogger(GoogleOAuthController.class);
	
    @GetMapping("/auth/google")
    public void loginPage(HttpServletRequest request, HttpServletResponse response){
		try {
			response.sendRedirect( oAuthService.getRequestLoginUrl());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @GetMapping("/auth/google/callback")
    public ModelAndView requestAccessCallback( @RequestParam(value = "code") String code,
											   @RequestParam(value = "state") String state){
        GoogleOAuthResponse dto = null;
		try{
			dto = (GoogleOAuthResponse) oAuthService.requestAccessToken(code, state);
			logger.debug("===================================");
			logger.debug("tokenType : {} ", dto.getToken_type());
			logger.debug("scope : {} ", dto.getScope());
			logger.debug("IdToken : {} ", dto.getId_token());
			logger.debug("AccessToken :  {}", dto.getId_token());
			logger.debug("RefreshToken {} ", dto.getRefresh_token());
			logger.debug("ExpiresIn {} ", dto.getExpires_in());	
			
			logger.debug("UserInfo",  oAuthService.getUserInfo(dto.getId_token()));	
		} catch(Exception ex){
			// 구글 로그인 실패처리
		}
		System.out.println(oAuthService.getUserInfo(dto.getId_token()));
		// ModelAndView mv = new ModelAndView("redirect:/auth/google/success");
		ModelAndView mv = new ModelAndView("google");
		// mv.addObject("accessToken", dto.getAccessToken());
		mv.addObject("userInfo", oAuthService.getUserInfo(dto.getId_token()));

		return mv;
    }

     /**
	 * 토큰 무효화 
	 **/
	@GetMapping("/auth/google/revoke/token")
    @ResponseBody
	public Map<String, String> revokeToken(@RequestParam(value = "token") String token) throws JsonProcessingException {

		Map<String, String> result = new HashMap<>();
		RestTemplate restTemplate = new RestTemplate();
		final String requestUrl = UriComponentsBuilder.fromHttpUrl(GOOGLE_REVOKE_TOKEN_BASE_URL)
				.queryParam("token", token).encode().toUriString();

		System.out.println("TOKEN ? " + token);
		String resultJson = restTemplate.postForObject(requestUrl, null, String.class);
		result.put("result", "success");
		result.put("resultJson", resultJson);

		return result;
	}
   

}
