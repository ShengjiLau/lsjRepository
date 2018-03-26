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
        Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        if (credentials == null) {
            return null;
        }else{
            UserCompRel userCompRel = (UserCompRel) credentials;
            return userCompRel;
        }
    }

}
