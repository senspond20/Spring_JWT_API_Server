package com.sens.pot.web.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    
    @RequestMapping("/api/dfdf")
    public String testfe(){
        return "backup";
    }   
}
