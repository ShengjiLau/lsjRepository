package com.lcdt.web.controller.register;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class RegisterController {


    @RequestMapping("/register")
    public String helloHtml(Map<String,Object> map){

        map.put("hello","dfsafda");
        return"/register/signup";
    }

}
