package com.lcdt.login.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("error")
public class ErrorController {

    String errorPage = "/error";

    @RequestMapping("400")
    public String handle1(HttpServletRequest request){
        return errorPage;
    }

    @RequestMapping("404")
    public String handle2(HttpServletRequest request){
        return errorPage;
    }

    @RequestMapping("500")
    public String handle3(HttpServletRequest request){
        return errorPage;
    }

}
