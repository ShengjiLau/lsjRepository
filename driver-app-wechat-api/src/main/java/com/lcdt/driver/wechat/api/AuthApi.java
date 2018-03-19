package com.lcdt.driver.wechat.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.userinfo.exception.PassErrorException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthApi {

    @Reference
    UserService userService;

    @RequestMapping("/login")
    public User login(String phone,String pwd){
        try {
            User user = userService.userLogin(phone, pwd);
            return user;
        } catch (UserNotExistException e) {
            e.printStackTrace();
        } catch (PassErrorException e) {
            e.printStackTrace();
        }
        return null;
    }




}
