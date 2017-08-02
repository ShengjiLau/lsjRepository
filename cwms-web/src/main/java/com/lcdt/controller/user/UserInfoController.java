package com.lcdt.controller.user;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.userinfo.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ybq on 2017/8/1.
 */

@RestController
public class UserInfoController {

    @Reference
    private UserService userService;

    @RequestMapping("/user/{id}")
    public boolean view(@PathVariable("id") Long id) {

        return  userService.checkUserLogin();
    }

}


