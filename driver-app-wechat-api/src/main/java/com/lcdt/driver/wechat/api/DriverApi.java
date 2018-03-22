package com.lcdt.driver.wechat.api;

import com.lcdt.userinfo.model.User;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/driver")
public class DriverApi {

    @RequestMapping("/info")
    private String driverInfo(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "this is driver info" + user.getPhone() + "userId: " + user.getUserId();
    }


}
