package com.sens.pot.service.opendatapotal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;


public class OpenDataPotalService {
    
    private final String ENCODE_SCRECT_KEY ="pikPyqBgw3tFQn4GlCCkcGLnhG0S%2BDrdo676M8ePXOpPnwVKDziXnpF4H%2F%2Fc6lqBwC3RL9Kn07yfPBCzr1YHgQ%3D%3D";
    private final String DECODE_SCRECT_KEY ="pikPyqBgw3tFQn4GlCCkcGLnhG0S+Drdo676M8ePXOpPnwVKDziXnpF4H//c6lqBwC3RL9Kn07yfPBCzr1YHgQ==";

    protected ResponseEntity<?> responseData(String endPoint){
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(endPoint);
        factory.setEncodingMode(EncodingMode.NONE);
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("ServiceKey", ENCODE_SCRECT_KEY);
        params.add("numOfRows", Integer.toString(50));      // 한페이지 결과수
        params.add("pageNo", Integer.toString(1));          // 페이지 번호
        params.add("type", "json");
        params.add("srchCTId", "L01");          //지역코드명
        UriBuilder uri = factory.builder().queryParams(params);
        System.out.println(uri.build());
        RestTemplate template = new RestTemplate();
        ResponseEntity<?> response = template.getForEntity(uri.build(), String.class);
        
        return response;
    }

    
    protected ResponseEntity<?> responseData(String endPoint, MultiValueMap<String,String> params){
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(endPoint);
        factory.setEncodingMode(EncodingMode.NONE);
        params.add("ServiceKey", ENCODE_SCRECT_KEY);
        params.add("type", "json");
        UriBuilder uri = factory.builder().queryParams(params);
        System.out.println(uri.build());
        RestTemplate template = new RestTemplate();
        ResponseEntity<?> response = template.getForEntity(uri.build(), String.class);
        return response;
    }

    

    protected List<Map<String,String>> jsonArraytoListmapTest(String jsonArrayStr){

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


}
