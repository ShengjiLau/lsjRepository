package com.lcdt.clms.permission.service;


import com.lcdt.clms.permission.dto.AdminPermissionQueryDto;
import com.lcdt.clms.permission.model.AdminPermission;

import java.util.List;

public interface AdminPermissionService {

    int deleteByPrimaryKey(Long adminPermissionId);

    int insert(AdminPermission record);

    AdminPermission selectByPrimaryKey(Long adminPermissionId);

    List<AdminPermission> selectAll(AdminPermissionQueryDto queryDto);

    int updateByPrimaryKey(AdminPermission record);
}