package com.lcdt.web.controller.register;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Reference
    private UserService userService;

    /***
     * 注册首页
     * @param map
     * @return
     */
    @RequestMapping(path = "/index")
    public String index(Map<String,Object> map){
        return"/register/signup";
    }

    /***
     * 注册提交
     * @param registerDto
     * @return
     */
    @RequestMapping(path = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String toSave(RegisterDto registerDto) {
        System.out.println(111111);
        System.out.println(registerDto.getName());
        return registerDto.getName();

    }





}
