package com.lcdt.web.controller.user;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ybq on 2017/8/1.
 */

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Reference
    private UserService userService;

    @RequestMapping("/register_page")
    public void registerPage(){

    }


    @RequestMapping("/registeruser")
    public void registerUser(RegisterDto registerDto){
        try {
            userService.registerUser(registerDto);
        } catch (PhoneHasRegisterException e) {
            e.printStackTrace();
        }
    }

}


