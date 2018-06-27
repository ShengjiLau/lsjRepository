package com.lcdt.clms.permission.service.impl;

import com.lcdt.clms.permission.dao.AdminPermissionMapper;
import com.lcdt.clms.permission.dto.AdminPermissionQueryDto;
import com.lcdt.clms.permission.model.AdminPermission;
import com.lcdt.clms.permission.service.AdminPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/6/21.
 */
@Service
public class AdminPermissionServiceImpl implements AdminPermissionService {

    @Autowired
    private AdminPermissionMapper adminPermissionMapper;


    @Override
    public int deleteByPrimaryKey(Long adminPermissionId) {
        return adminPermissionMapper.deleteByPrimaryKey(adminPermissionId);
    }

    @Override
    public int insert(AdminPermission record) {
        return adminPermissionMapper.insert(record);
    }

    @Override
    public AdminPermission selectByPrimaryKey(Long adminPermissionId) {
        return adminPermissionMapper.selectByPrimaryKey(adminPermissionId);
    }

    @Override
    public List<AdminPermission> selectAll(AdminPermissionQueryDto queryDto) {
        return adminPermissionMapper.selectAll(queryDto);
    }

    @Override
    public int updateByPrimaryKey(AdminPermission record) {
        return adminPermissionMapper.updateByPrimaryKey(record);
    }
}
