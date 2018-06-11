package com.lcdt.userinfo.web.controller.api.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dao.UserMapper;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.web.dto.UserQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserManageApi {

    @Autowired
    UserMapper mapper;

    public List<User> list(UserQueryDto userQueryDto){
        PageInfo<User> objectPageInfo = PageHelper.startPage(userQueryDto.getPageNo(), userQueryDto.getPageSize()).doSelectPageInfo(() -> mapper.selectByUserDto(userQueryDto));
        return new ArrayList<>();
    }

    public User updateStatus(UserQueryDto userQueryDto) {
        User user = mapper.selectByPrimaryKey(userQueryDto.getUserId());
//        user.setUserStatus();
        return new User();
    }


}
