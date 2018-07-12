package com.lcdt.driver.wechat.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.token.config.JwtTokenUtil;
import com.lcdt.driver.dto.WechatUserDto;
import com.lcdt.notify.rpcservice.IValidCodeService;
import com.lcdt.notify.rpcservice.ValidCodeExistException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthApi {

    @Reference
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Reference
    IValidCodeService validCodeService;

    @PostMapping("/login")
    public JSONObject login(@Valid @RequestBody WechatUserDto wechatUserDto) {
        JSONObject jsonObject = new JSONObject();
        String validcode = wechatUserDto.getValidCode();
        String phone = wechatUserDto.getPhone();
       boolean codeCorrect = validCodeService.isCodeCorrect(validcode, VCODETAG, phone);
       if (!codeCorrect) {
            jsonObject.put("result", -1);
            jsonObject.put("message", "验证码错误");
           return jsonObject;
        }
        User user=null;
        try {
            user = userService.queryByPhone(phone);
        } catch (UserNotExistException e) {
            user = new User();
            user.setPhone(phone);
            if (wechatUserDto != null) {
                user.setNickName(wechatUserDto.getNickName());
                user.setRealName(wechatUserDto.getNickName());
                user.setPictureUrl(wechatUserDto.getAvatarUrl());
                if (StringUtils.isEmpty(wechatUserDto.getNickName())) {
                    user.setNickName("老司机");
                    user.setRealName(phone);
                }
            }
            user.setRegisterFrom("司机宝小程序");
            userService.registerDriverUser(user);
        }
        user.setPwd("");
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("userName", user.getPhone());
        String s = jwtTokenUtil.generateToken(stringStringHashMap);
        jsonObject.put("token", s);
        jsonObject.put("result", 0);
        jsonObject.put("user", user);
        jsonObject.put("message", "请求成功");
        return jsonObject;
    }

    public static final String VCODETAG = "driverwechat";


    @RequestMapping(value = "/sendvcode",method = RequestMethod.POST)
    public JSONObject sendVcode(String phone) {
        JSONObject jsonObject = new JSONObject();
        try {
            String s = validCodeService.sendValidCode(VCODETAG, 15 * 60, phone);
            jsonObject.put("data", s);
            jsonObject.put("result", 0);
            jsonObject.put("message", "请求成功");
            return jsonObject;
        } catch (ValidCodeExistException e) {
            e.printStackTrace();
            jsonObject.put("result", -1);
            jsonObject.put("message", "已发送");
        }
        return jsonObject;
    }

}
