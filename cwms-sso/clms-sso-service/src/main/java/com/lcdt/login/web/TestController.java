package com.lcdt.login.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @RequestMapping("/testUrl")
    public String testUrlUtils(HttpServletRequest request) {
        String callback = "http://" + request.getServerName() //服务器地址
                + ":"
                + request.getServerPort()           //端口号
                + request.getContextPath()      //项目名称
                + request.getServletPath();


                //请求页面或其他地址
        if (request.getQueryString() != null) {
            callback = callback + "?" + request.getQueryString();
        }

        return callback;
    }

}
