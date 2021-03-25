package com.sens.pot.web.api;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sens.pot.web.domain.test.Test;
import com.sens.pot.web.repository.content.ContentMapper;
import com.sens.pot.web.repository.content.PostsMapper;
import com.sens.pot.web.repository.test.TestMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InitController {
    
    private final TestMapper testMapper;

    private final ContentMapper contentMapper;
    @GetMapping("/mybatisTest")
    public List<Test> test(){
        System.out.println(testMapper.select_TestAll());
        return testMapper.select_TestAll();
    }

    @GetMapping("/posts")
    public Object getPost(){
        return contentMapper.select_PostsList();
    }

    @GetMapping("/hello")
    public Map<String,Object> hello(){
        Map<String,Object> map = new HashMap<>();
        map.put("msg", "안녕하세요");
        map.put("time", LocalDate.now());
        return map;
    }
    
}
