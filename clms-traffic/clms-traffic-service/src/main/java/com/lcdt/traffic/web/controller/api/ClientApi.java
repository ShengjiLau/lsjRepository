package com.lcdt.traffic.web.controller.api;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import com.lcdt.util.WebProduces;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangbinq on 2017/11/20.
 */
@RestController
@RequestMapping("/api/traffic/client")
@Api(value = "客户接口",description = "运输客户模块接口")
public class ClientApi {

    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/get", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    public User getUserInfo() {
        User user = SecurityInfoGetter.getUser();
        //将密码设置为空字符串
        user.setPwd("");
        return user;
    }
}
