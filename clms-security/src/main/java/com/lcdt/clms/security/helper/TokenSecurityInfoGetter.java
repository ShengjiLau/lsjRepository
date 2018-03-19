package com.lcdt.clms.security.helper;

import com.lcdt.userinfo.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class TokenSecurityInfoGetter {

    public static User getUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

}
