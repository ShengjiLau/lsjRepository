package com.lcdt.userinfo.web.controller.api.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dao.UserMapper;
import com.lcdt.userinfo.model.AdminUser;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.utils.JSONResponseUtil;
import com.lcdt.userinfo.utils.ResponseMessage;
import com.lcdt.userinfo.web.dto.UserQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class UserManageApi {

    @Autowired
    UserMapper mapper;

    @PostMapping("/list")
    public ResponseMessage list(UserQueryDto userQueryDto){
        PageInfo<AdminUser> pageInfo = PageHelper.startPage(userQueryDto.getPageNo(), userQueryDto.getPageSize()).doSelectPageInfo(() -> mapper.selectByUserDto(userQueryDto));
        return JSONResponseUtil.success(pageInfo);
    }

    @PostMapping("/updateStatus")
    public ResponseMessage updateStatus(UserQueryDto userQueryDto) {
        User user = mapper.selectByPrimaryKey(userQueryDto.getUserId());
        user.setUserStatus(userQueryDto.getUserStatus());
        mapper.updateByPrimaryKey(user);
        return JSONResponseUtil.success(user);
    }


}
