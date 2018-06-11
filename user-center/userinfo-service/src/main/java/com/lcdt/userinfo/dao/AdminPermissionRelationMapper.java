package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.AdminPermissionRelation;

import java.util.List;

public interface AdminPermissionRelationMapper {
    int deleteByPrimaryKey(Long relationId);

    int insert(AdminPermissionRelation record);

    AdminPermissionRelation selectByPrimaryKey(Long relationId);

    List<AdminPermissionRelation> selectAll();

    int updateByPrimaryKey(AdminPermissionRelation record);
}