package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.AdminUser;

import java.util.List;

public interface AdminUserMapper {

    List<AdminUser> selectByUserId(Long userId);

}
