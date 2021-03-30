package com.sens.pot.http;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class RestClientTest {
    // @Autowired
    // private RestOperations rest;
    private final String NAVER_PROFILE_URL="https://openapi.naver.com/v1/nid/me";
    private HttpHeaders getHeaders(){
        String accessToken="AAAANwBevCiSIpaOPkczl0aSsN1YszI503e-LF9um6FlxVl9K-IQG_2hmo0kiu9EN3a1Q5fVd3r7BQt_3pR22huy6RY";
        String base64ClientCredentials = new String(Base64.encodeBase64(accessToken.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64ClientCredentials);
        return headers;
    }

    @Test
    public void test() {
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Barer " + "AAAANycANC5jDR9nfTVWG3b1K3PtL3DmwadR5h43WgUOiYLdPt6IgReu4IFHtJUAXr2eQyFc8sAwIMtmeJPy1H5PjPk");
        ResponseEntity<String> response = new RestTemplate().exchange(NAVER_PROFILE_URL, HttpMethod.GET, request, String.class);
        System.out.println(response.getBody());
    }
}
