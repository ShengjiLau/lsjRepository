package com.lcdt.clms.security.helper;

import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import org.springframework.security.core.context.SecurityContextHolder;

public class TokenSecurityInfoGetter {

    public static User getUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    public UserCompRel getUserCompRel(){
        UserCompRel userCompRel = (UserCompRel) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return userCompRel;
    }

}
