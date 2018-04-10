package com.lcdt.driver.wechat.api.DriverManageApi;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.driver.dto.ForgetPwdDto;
import com.lcdt.notify.rpcservice.IValidCodeService;
import com.lcdt.userinfo.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth/api/manage/")
public class UserApi {

    @Reference
    IValidCodeService validCodeService;

    @Reference
    UserService userService;

    @RequestMapping("/forgetpwd")
    public String forgetPwd(ForgetPwdDto forgetPwdDto){
        boolean codeCorrect = validCodeService.isCodeCorrect(forgetPwdDto.getValidCode(), "", forgetPwdDto.getUserName());
        JSONObject jsonObject = new JSONObject();
        if (codeCorrect) {
            userService.resetPwd(forgetPwdDto.getUserName(), forgetPwdDto.getPwd());
            jsonObject.put("message", "设置成功");
            jsonObject.put("code", 0);
        }else{
            jsonObject.put("message", "验证码错误");
            jsonObject.put("code", -1);
        }
        return jsonObject.toString();
    }

}
