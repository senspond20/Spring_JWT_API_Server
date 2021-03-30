package com.sens.pot.service.social;
// 네이버 API 예제 - 회원프로필 조회
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;




public class ApiExamMemberProfile {
    public static Map<String,String> jsonStrToMap(String jsonStr){
        Map<String,String> map = new LinkedHashMap<String,String>();

        // "[\"][^\\,\\{\\}]*" 패턴으로 매칭을 한다.
        Pattern pattern = Pattern.compile("[\"][^\\,\\{\\}]*"); 
        Matcher matcher = pattern.matcher(jsonStr);

        while(matcher.find()){
             //" : " 을 뽑은 다음
             // "를 없애고 : 를 구분자로 나눈다.
              String[] mk = matcher.group().replaceAll("\"", "").split("\\:"); 
            
          // key, value가 공백이 아닌 값만 취한다.
          String k = mk[0].trim();
          String v = mk[1].trim();
           if(!k.equals("") && !v.equals(""))
             map.put(k, v);
        }
        return map;
    }

    public static String test(){
        String token = "AAAANycANC5jDR9nfTVWG3b1K3PtL3DmwadR5h43WgUOiYLdPt6IgReu4IFHtJUAXr2eQyFc8sAwIMtmeJPy1H5PjPk"; // 네이버 로그인 접근 토큰;
        String header = "Bearer " + token; // Bearer 다음에 공백 추가
        String apiURL = "http://senspond.iptime.org:8083/api/account/read?email=senspond@gmail.com";
                MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("func_name", "cv");
                
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", header);

        HttpEntity<?> formEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(apiURL, String.class);
        return response.getBody();
    }
    public static void main(String[] args) throws UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
        String token = "AAAANycANC5jDR9nfTVWG3b1K3PtL3DmwadR5h43WgUOiYLdPt6IgReu4IFHtJUAXr2eQyFc8sAwIMtmeJPy1H5PjPk"; // 네이버 로그인 접근 토큰;
        String header = "Bearer " + token; // Bearer 다음에 공백 추가
        // String apiURL = "https://openapi.naver.com/v1/nid/me";
        String apiURL = "http://senspond.iptime.org:8083/api/account/read?email=senspond@gmail.com";
        


        Map<String,String> requestHeaders = new HashMap<String,String>();
        System.out.println("================================================================================================");
        requestHeaders.put("Authorization", header);
        // String responseBody = get(apiURL,requestHeaders);
        // System.out.println(responseBody);
        System.out.println("================================================================================================");

        String sss = test();
        System.out.println(sss);
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(sss, new TypeReference<Map<String,Object>>(){});
        String ssss = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);

        System.out.println(ssss);

    //     System.out.println(response.getBody());

    //     // String response = restTemplate.postForObject(apiURL, httpEntity, String.class);
    //     // System.out.println(response);
    //    String ss = new String("\ud669\uc774\uac74".getBytes("utf-8"), "euc-kr");
    //     System.out.println(ss);


    }


    private static String get(String apiUrl, Map<String, String> requestHeaders){
       
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            // con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Type", "application/json");
            // con.setRequestProperty("Accept-Encoding", "gzip, deflate, br"); 
            // con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            // con.setRequestProperty("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }
   
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
      

        try (
            InputStreamReader streamReader = new InputStreamReader(body, "UTF-8");
            BufferedReader lineReader = new BufferedReader(streamReader);
            
            ) {
            StringBuilder responseBody = new StringBuilder();
            int ch;
            while ((ch = lineReader.read()) != -1) {
                responseBody.append((char) ch);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}