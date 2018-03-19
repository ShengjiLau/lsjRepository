package com.lcdt.driver.wechat.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.clms.security.token.config.JwtTokenUtil;
import com.lcdt.userinfo.exception.PassErrorException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthApi {

    @Reference
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @RequestMapping("/login")
    public String login(String phone,String pwd){
        try {
            User user = userService.userLogin(phone, pwd);
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("userName", user.getPhone());
            String s = jwtTokenUtil.generateToken(stringStringHashMap);
            return s;
        } catch (UserNotExistException e) {
            e.printStackTrace();
        } catch (PassErrorException e) {
            e.printStackTrace();
        }
        return null;
    }




}
