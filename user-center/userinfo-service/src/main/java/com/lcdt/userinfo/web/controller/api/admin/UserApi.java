package com.lcdt.userinfo.web.controller.api.admin;

import com.lcdt.userinfo.dao.UserMapper;
import com.lcdt.userinfo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserApi {

    @Autowired
    UserMapper mapper;

    public List<User> list(){



        return new ArrayList<>();
    }


}
