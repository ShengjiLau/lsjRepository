package com.lcdt.clms.permission.dao;



import com.lcdt.clms.permission.model.AdminPermission;

import java.util.List;

public interface AdminPermissionMapper {
    int deleteByPrimaryKey(Long adminPermissionId);

    int insert(AdminPermission record);

    AdminPermission selectByPrimaryKey(Long adminPermissionId);

    List<AdminPermission> selectAll();

    int updateByPrimaryKey(AdminPermission record);
}