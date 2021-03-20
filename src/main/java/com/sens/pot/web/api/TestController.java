package com.sens.pot.web.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class TestController {



    @GetMapping("/test")
    public String test(){
        // System.out.println(test);
        return "test";
    }
}
