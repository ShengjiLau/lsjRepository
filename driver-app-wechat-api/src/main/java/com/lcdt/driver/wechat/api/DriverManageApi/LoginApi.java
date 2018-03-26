package com.lcdt.driver.wechat.api.DriverManageApi;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PassErrorException;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/api/manage/")
public class LoginApi {

    @Reference
    UserService userService;

    @RequestMapping("/register")
    public User registerUser(RegisterDto registerDto) {
        try {
            User user = userService.registerUser(registerDto);
            return user;
        } catch (PhoneHasRegisterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/login")
    public User login(String userName, String pwd) throws UserNotExistException, PassErrorException {
        User user = userService.userLogin(userName, pwd);
        return user;
    }

    @RequestMapping("/complist")
    public PageBaseDto<UserCompRel> compList(){

        return null;
    }


    @RequestMapping("/choosecomp")

    public UserCompRel chooseCompany() {
        return null;
    }


}
