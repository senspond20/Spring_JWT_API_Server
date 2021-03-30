package com.sens.pot.utils;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

public class JsonUtils {
      /**
     * 정규표현식 파싱 
     * @param jsonStr
     * @return
     */
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

    /**
     * Jackson 라이브러리
     * @param jsonStr
     * @return
     */
    public static Map<String,String> jsonStrToMap2(String jsonStr){
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> map = null;
        try {
            map = mapper.readValue(jsonStr, new TypeReference<Map<String, String>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return map;
    }

    public List<Map<String,String>> jsonArraytoListmapTest(String jsonArrayStr){

        List<Map<String,String>> list = new ArrayList<>();    
        Pattern pattern = Pattern.compile("\\{[^\\{\\}]*\\}");  // { } 리스트 
        Pattern sPattern = Pattern.compile("[\"][^\\,\\{\\}]*"); // " : " 리스트
        Matcher matcher = pattern.matcher(jsonArrayStr);
        Matcher sMatcher = null;
        Map<String,String> map = null;
        
        while(matcher.find()){
            // map = new HashMap<String, Object>();    hashMap은 순서 보장을 하지 않지만 속도가 더 빠르다.
            map = new LinkedHashMap<String, String>(); 
            sMatcher = sPattern.matcher(matcher.group()); // { } 을 뽑고
            while (sMatcher.find()) {
                String[] mk = sMatcher.group().replaceAll("\"", "").split("\\:"); 
                String k = mk[0].trim();
                String v = mk[1].trim();
                if(!k.equals("") && !v.equals(""))
                    map.put(k, v);
            }
            if(map!=null)
              list.add(map);
        }
        return list;
    }
    @Test
    void test2() throws JsonProcessingException{
        String jsonStr = "{\"comment\":1,\"comment\" :[{\"id\":1,\"email\":\"test@gmail.com\",\"nickname\": \"dgfgg\"},{\"id\":2,\"email\":\"test2@gmail.com\",\"nickname\": \"하나\"},{\"id\":3,\"email\":\"test3@gmail.com\",\"nickname\": \"길 K\"}]";
        List<Map<String,String>> list = jsonArraytoListmapTest(jsonStr);
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(list));
        System.out.println("=================================================");
        System.out.println(list);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));

        System.out.println(list.get(0).get("email"));
        System.out.println(list.get(1).get("id"));
        System.out.println(list.get(2).get("nickname"));
    }
    @Test
    void test() throws JsonProcessingException {
        String jsonStr = "{\"id\":1,\"email\":\"test@gmail.com\",\"nickname\": \"dgfgg\"}";
        // String jsonOStr = "{\"type\" : \"success\",\"value\": {\"id\" : 9,\"quote\" : \"So easy\"}}";
        // String jsonErStr = "id\":1,\"email\":sdfsdfddsfdsfdnickname\": \"dgfgg";
        // String jsonStrError = "O";
        // String test = "{\"resultcode\":\"00\",\"message\":\"success\",\"response\":{\"id\":\"73250768\",\"nickname\":\"Lux Aeterna\",\"email\":\"senshig@naver.com\",\"name\":\"\ud669\uc774\uac74\"}}";
        String test = "{\"resultcode\":\"00\",\"message\":\"success\",\"response\":\"df\"}";
      
        System.out.println("================================");
        Map<String,String> map1 = jsonStrToMap(test);
        System.out.println(map1);
        // System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map1));
        System.out.println("================================");
        Map<String,String> map2 = jsonStrToMap2(jsonStr);
        System.out.println(map2.get("value"));
        // System.out.println(new ObjectMapper().writeValueAsString(map1));
       

        // assertTrue(map1.get("email").equals(map2.get("email")));
    }

    @Test
    void test4() throws JsonProcessingException{
        // String jsonStr = "{\"comment\":1,\"comment\" :[{\"id\":1,\"email\":\"test@gmail.com\",\"nickname\": \"dgfgg\"},{\"id\":2,\"email\":\"test2@gmail.com\",\"nickname\": \"하나\"},{\"id\":3,\"email\":\"test3@gmail.com\",\"nickname\": \"길 K\"}]";

        String jsonStr = "{\"comment\":1,\"comment\" :[{\"id\":1,\"email\":\"test@gmail.com\",\"nickname\": \"dgfgg\"},{\"id\":2,\"email\":\"test2@gmail.com\",\"nickname\": \"하나\"},{\"id\":3,\"email\":\"test3@gmail.com\",\"nickname\": \"길 K\"}],\"test\" :[{\"id\" :\"feef\"},{\"id\":\"fgfg\"}]";
        List<Map<String,String>> list = jsonArraytoListmapTest(jsonStr);
        System.out.println(new ObjectMapper().writeValueAsString(list));
        System.out.println(list);
        System.out.println("==================================================");
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));
        System.out.println(list.get(4));
        // System.out.println(list.get(5));
 

    }
}
