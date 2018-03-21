package com.lcdt.driver.wechat.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.token.config.JwtTokenUtil;
import com.lcdt.driver.dto.WechatUserDto;
import com.lcdt.userinfo.exception.PassErrorException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthApi {

    @Reference
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @RequestMapping("/login")
    public String login(String phone, String validcode, WechatUserDto wechatUserDto) {
        JSONObject jsonObject = new JSONObject();
        User user;
        try {
            user = userService.queryByPhone(phone);
        } catch (UserNotExistException e) {
            user = new User();
            user.setPhone(phone);
            user.setNickName(wechatUserDto.getNickName());
            user.setRealName(wechatUserDto.getNickName());
            userService.registerDriverUser(user);
        }
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("userName", user.getPhone());
        String s = jwtTokenUtil.generateToken(stringStringHashMap);
        jsonObject.put("token", s);
        jsonObject.put("result", 0);
        jsonObject.put("user", user);
        jsonObject.put("message", "请求成功");
        return jsonObject.toString();
    }

}
