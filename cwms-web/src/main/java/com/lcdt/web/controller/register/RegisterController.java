package com.lcdt.web.controller.register;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.notify.service.SmsService;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Reference
    private UserService userService;

    @Reference
    private SmsService smsService;

    /***
     * 注册首页
     * @return
     */
    @RequestMapping(path = "/index")
    public ModelAndView index(){
        return new ModelAndView("/register/signup");
    }

    /***
     * 注册提交
     * @param registerDto
     * @return
     */
    @RequestMapping(path = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String toSave(RegisterDto registerDto) {
       System.out.println(registerDto.getName());
        return registerDto.getName();
    }

    /***
     * 发信短信码
     * @return
     */
    @RequestMapping(path = "/vSmsCode")
    @ResponseBody
    public String vCode() {
        System.out.println(userService);
        System.out.println(smsService);
       // smsService.findSmsBalance();

        return "";
    }




}
