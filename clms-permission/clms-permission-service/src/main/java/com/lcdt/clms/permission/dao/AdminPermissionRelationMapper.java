package com.lcdt.clms.permission.dao;

import com.lcdt.clms.permission.model.AdminPermissionRelation;

import java.util.List;

public interface AdminPermissionRelationMapper {
    int deleteByPrimaryKey(Long relationId);

    int insert(AdminPermissionRelation record);

    AdminPermissionRelation selectByPrimaryKey(Long relationId);

    List<AdminPermissionRelation> selectAll();

    int updateByPrimaryKey(AdminPermissionRelation record);

    List<AdminPermissionRelation> selectByUserId(Long userId);
}