package com.sens.pot.http;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

public class OpenDataTest {
    
    private final String ENCODE_SCRECT_KEY ="pikPyqBgw3tFQn4GlCCkcGLnhG0S%2BDrdo676M8ePXOpPnwVKDziXnpF4H%2F%2Fc6lqBwC3RL9Kn07yfPBCzr1YHgQ%3D%3D";
    private final String DECODE_SCRECT_KEY ="pikPyqBgw3tFQn4GlCCkcGLnhG0S+Drdo676M8ePXOpPnwVKDziXnpF4H//c6lqBwC3RL9Kn07yfPBCzr1YHgQ==";


    @Test
    void test(){
        String baseUrl = "http://apis.data.go.kr/B551553/TradFoodInfoService";
        // String baseUrl = "https://tradifood.net/api/service/TradFoodInfoService";
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);

        factory.setEncodingMode(EncodingMode.NONE);
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("ServiceKey", ENCODE_SCRECT_KEY);
        params.add("numOfRows", Integer.toString(10));
        params.add("pageNo", Integer.toString(1));
        params.add("type", "JSON");
        params.add("SG_APIM", "2ug8Dm9qNBfD32JLZGPN64f3EoTlkpD8kSOHWfXpyrY");
        UriBuilder uri  = factory.builder()
                                 .path("/getFoodCateogryList")
                                 .queryParams(params);
        RestTemplate template = new RestTemplate();
        ResponseEntity<?> response = template.getForEntity(uri.build(), String.class);
        System.out.println(response.getBody());
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

    /**
     * @param tableName : 테이블 명
     * @param list      : List<Map<String,String>> 형태의 맵
     * @param startNum  : 시작 index
     * @return
     */
    String insertIntoTable(String tableName, List<Map<String,String>> list, int startNum){
        Set<String> keySet = list.get(startNum).keySet();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT\sINTO\s").append(tableName).append("\s")
                                 .append(keySet.toString().replaceAll("\\[", "(\s").replaceAll("\\]", "\s)"))
                                 .append("\sVALUES\n");
        for(int i = startNum; i < list.size(); i ++){
            sb.append("(\s");
            Iterator<String> it =  keySet.iterator();
            int count = 0;
            while(it.hasNext()){
                sb.append(list.get(i).get(it.next()));
                if(count != keySet.size()-1){
                    sb.append(",\s");
                }else{
                    sb.append("\s");
                }
                count++;
            }
            if(i != list.size()-1){
                sb.append("),\n");
            }else{
                sb.append(");");
            }
        }
        final String result = sb.toString();
        sb = null;
        return result;
    };

    @Test
    void coronaTest(){
        final String baseUrl = "http://apis.data.go.kr/1320000/PlanCrossRoadInfoService/getPlanCRHDInfo";

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
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

        if(response.getStatusCode() == HttpStatus.OK){
		    String jsonStr = response.getBody().toString();
		    //System.out.println(jsonStr);

            List<Map<String,String>> list = jsonArraytoListmapTest(jsonStr);
            // list.forEach(item->{
            //     System.out.println(item);
            // });
            String query = insertIntoTable("new_table", list, 1);
            System.out.println(query);
            

		    // Map<String,Object> map = new ObjectMapper().readValue(jsonStr, new TypeReference<Map<String, Object>>() {});
		    // System.out.println(map);
		}else{
            System.out.println(response.getBody());
		} 
        // System.out.println(response.getBody());


    }

}
