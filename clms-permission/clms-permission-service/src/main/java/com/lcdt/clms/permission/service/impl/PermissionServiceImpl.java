package com.lcdt.clms.permission.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.permission.dao.PermissionMapper;
import com.lcdt.clms.permission.dto.PermissionParamsDto;
import com.lcdt.clms.permission.model.Permission;
import com.lcdt.clms.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lyqishan on 2018/7/16
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public int addPermission(Permission permission) {
        return permissionMapper.insert(permission);
    }

    @Override
    public int deletePermission(Integer permissionId) {
        return permissionMapper.deleteByPrimaryKey(permissionId);
    }

    @Override
    public int modifyPermission(Permission permission) {
        return permissionMapper.updateByPrimaryKey(permission);
    }

    @Override
    public Permission queryPermission(Integer permissionId) {
        return permissionMapper.selectByPrimaryKey(permissionId);
    }

    @Override
    public PageInfo<Permission> queryPermissionList(PermissionParamsDto params) {
        PageHelper.startPage(params.getPageNo(), params.getPageSize());
        return new PageInfo<>(permissionMapper.selectPermissionByCondition(params));
    }
}
