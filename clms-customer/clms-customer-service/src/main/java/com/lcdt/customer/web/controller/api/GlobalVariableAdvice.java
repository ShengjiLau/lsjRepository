package com.lcdt.customer.web.controller.api;

import com.alibaba.dubbo.common.utils.UrlUtils;
import com.lcdt.customer.config.ConfigConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice("com.lcdt.customer.web.controller.api")
public class GlobalVariableAdvice {

    @Autowired
    ConfigConstant configConstant;

    @ModelAttribute("logouturl")
    public String LogoutHost(){
        String logoutHost = configConstant.logoutHost;
        return logoutHost;
    }

}
