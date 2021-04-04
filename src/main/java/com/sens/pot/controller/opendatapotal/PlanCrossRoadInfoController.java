package com.sens.pot.controller.opendatapotal;

import com.sens.pot.service.opendatapotal.PlanCorssRoadInfoService;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PlanCrossRoadInfoController {
 
    private final PlanCorssRoadInfoService planCorssRoadInfoService;

    @PostMapping("/api/getplan")
    public Object getTest(@RequestParam Integer numOfRows, 
                          @RequestParam Integer pageNo, 
                          @RequestParam String srchCTId){

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("numOfRows", Integer.toString(numOfRows));      // 한페이지 결과수
        params.add("pageNo", Integer.toString(pageNo));          // 페이지 번호
        params.add("srchCTId", srchCTId);          //지역코드명
        return planCorssRoadInfoService.getPlanCRHDInfo(params);
    }


}
