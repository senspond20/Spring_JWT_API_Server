package com.sens.pot.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    
    private JsonUtils(){}


    /**
     * 내가 만든 코드
     * @param jsonStr
     * @return
     */
    public static Map<String,String> jsonStrToMap(String jsonStr){
        Map<String,String> map = new HashMap<String,String>();
        Pattern pattern = Pattern.compile("[\"][^\\,\\{\\}]*"); 
        Matcher matcher = pattern.matcher(jsonStr);

        while(matcher.find()){
             //" : " 을 뽑은 다음
             // "를 없애고 : 를 구분자로 나눈다.
              String[] mk = matcher.group().replaceAll("\"", "").split("\\:"); 
             map.put(mk[0], mk[1]);
        }
        return map;
    }

    public static Map<String,String> jsonStrToMap2(String jsonStr){
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> map = null;
        try {
            map = mapper.readValue(jsonStr, Map.class);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args) {
        String jsonStr = "{\"id\":1,\"email\":\"senspond@gmail.com\",\"nickname\": \"dgfgg\"}";

        // String jsonStr = "\"id\":1,\"email\":sdfsdfddsfdsfdnickname\": \"dgfgg";
        Map<String,String> map = jsonStrToMap(jsonStr);
        System.out.println(map);
    }

  
}
