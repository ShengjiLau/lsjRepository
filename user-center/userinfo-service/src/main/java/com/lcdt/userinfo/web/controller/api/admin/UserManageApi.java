package com.lcdt.userinfo.web.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dao.UserMapper;
import com.lcdt.userinfo.model.AdminUser;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.userinfo.utils.JSONResponseUtil;
import com.lcdt.userinfo.utils.ResponseMessage;
import com.lcdt.userinfo.web.controller.api.admin.dto.AdminUserDto;
import com.lcdt.userinfo.web.dto.UserQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user")
public class UserManageApi {

    @Autowired
    UserMapper mapper;

    @Autowired
    UserService userService;

    @PostMapping("/list")
    public ResponseMessage list(UserQueryDto userQueryDto){
        PageInfo<AdminUserDto> pageInfo = PageHelper.startPage(userQueryDto.getPageNo(), userQueryDto.getPageSize()).doSelectPageInfo(() -> mapper.selectByUserDto(userQueryDto));
        return JSONResponseUtil.success(pageInfo);
    }

    @PostMapping("/updateStatus")
    public ResponseMessage updateStatus(UserQueryDto userQueryDto) {
        User user = mapper.selectByPrimaryKey(userQueryDto.getUserId());
        user.setUserStatus(userQueryDto.getUserStatus());
        mapper.updateByPrimaryKey(user);
        return JSONResponseUtil.success(user);
    }

    @PostMapping("/manageList")
    public ResponseMessage manageList(UserQueryDto userQueryDto){
        PageInfo<AdminUserDto> pageInfo = PageHelper.startPage(userQueryDto.getPageNo(), userQueryDto.getPageSize()).doSelectPageInfo(() -> mapper.selectManageUserByUserDto(userQueryDto));
        return JSONResponseUtil.success(pageInfo);
    }

    @PostMapping("/addUserAdmin")
    public ResponseMessage addUserAdmin(AdminUser adminUser) {
        JSONObject jo = new JSONObject();
        if(adminUser == null || adminUser.getAdminUserId() == null){
            jo.put("code",-2);
            jo.put("message","参数不正确！");
        }
        try {
            userService.addUserAdmin(adminUser);
            jo.put("code",0);
        }catch(Exception e){
            e.printStackTrace();
            jo.put("code",-2);
            jo.put("message","设置后端用户失败！");
        }

        return JSONResponseUtil.success(adminUser);
    }

    @PostMapping("/deleteUserAdmin")
    public ResponseMessage deleteUserAdmin(AdminUser adminUser) {
        JSONObject jo = new JSONObject();
        if(adminUser == null || adminUser.getAdminUserId() == null || adminUser.getAdminId() != null){
            jo.put("code",-2);
            jo.put("message","参数不正确！");
        }
        try {
            userService.deleteUserAdmin(adminUser);
            jo.put("code",0);
        }catch(Exception e){
            e.printStackTrace();
            jo.put("code",-2);
            jo.put("message","取消后端用户失败！");
        }

        return JSONResponseUtil.success(adminUser);
    }



}
