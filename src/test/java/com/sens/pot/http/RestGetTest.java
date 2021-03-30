package com.sens.pot.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class RestGetTest {
    
    @Test
    void GetTest(){
        String request_url = "https://reqres.in/api/users";

        RestTemplate template = new RestTemplate();
        ResponseEntity<?> response = template.getForEntity(request_url, Map.class);
        try { 
            final String responsePretty =  new ObjectMapper().writerWithDefaultPrettyPrinter()
                                                             .writeValueAsString(response.getBody());
            System.out.println(responsePretty);
        } catch (RestClientException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 자바
     */
    HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    String readBody(InputStream body){
        try (
            InputStreamReader streamReader = new InputStreamReader(body);
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

    void printPrettyJsonStr(String jsonStr) throws JsonMappingException, JsonProcessingException{
        Map<String,Object> body = new ObjectMapper().readValue(jsonStr, new TypeReference<Map<String,Object>>(){});

        final String responsePretty =  new ObjectMapper().writerWithDefaultPrettyPrinter()
                                                         .writeValueAsString(body);
        System.out.println(responsePretty);
    }
    @Test
    void GetTest_J() throws IOException{
        String request_url = "http://senspot.tk:8083/api/account/read?email=test21@gmail.com";
        // String request_url = "https://reqres.in/api/users";
        URL url = new URL(request_url);
        HttpURLConnection conn = connect(url.toString());
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("GET");
        int responseCode = conn.getResponseCode();

        String response = (responseCode == HttpURLConnection.HTTP_OK)
                        ? readBody(conn.getInputStream())
                        : readBody(conn.getErrorStream());
        conn.disconnect();

        // System.out.println(response);
        System.out.println("=============================================");
        printPrettyJsonStr(response);
    }

}
