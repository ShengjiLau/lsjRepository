package com.lcdt.userinfo.service.impl;

import com.lcdt.userinfo.dao.TUserGroupWarehouseRelationMapper;
import com.lcdt.userinfo.localservice.GroupWareHouseService;
import com.lcdt.userinfo.model.TUserGroupWarehouseRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupWareHouseServiceImpl implements GroupWareHouseService{

    @Autowired
    TUserGroupWarehouseRelationMapper dao;

    @Override
    public List<TUserGroupWarehouseRelation> addedUserGroupWareHouse(Long companyId,Long groupId) {
        List<TUserGroupWarehouseRelation> tUserGroupWarehouseRelations = dao.selectAddedUserGroupWareHouse(groupId, companyId);
        if (tUserGroupWarehouseRelations == null) {
            return new ArrayList<TUserGroupWarehouseRelation>();
        }
        return tUserGroupWarehouseRelations;
    }

    @Override
    public List<TUserGroupWarehouseRelation> notAddUserGroupWareHouse() {
        return null;
    }
}
