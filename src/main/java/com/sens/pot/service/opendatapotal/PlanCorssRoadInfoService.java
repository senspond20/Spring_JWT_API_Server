package com.sens.pot.service.opendatapotal;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class PlanCorssRoadInfoService  extends OpenDataPotalService{
    final String baseUrl = "http://apis.data.go.kr/1320000/PlanCrossRoadInfoService";

    public List<Map<String,String>> getPlanCrossRoadInfoServiceTest(){
        final String endPoint = baseUrl + "/getPlanCRHDInfo";
        ResponseEntity<?> response = responseData(endPoint);
        if(response.getStatusCode() == HttpStatus.OK){
            return jsonArraytoListmapTest(response.getBody().toString());
        }else{
            throw new RuntimeException("에러");
        }
    }


    public List<Map<String,String>> getPlanCRHDInfo(MultiValueMap<String,String> params){
        final String endPoint = baseUrl + "/getPlanCRHDInfo";
        ResponseEntity<?> response = responseData(endPoint, params);
        if(response.getStatusCode() == HttpStatus.OK){
            return jsonArraytoListmapTest(response.getBody().toString());
        }else{
            throw new RuntimeException("에러");
        }
    }
}
