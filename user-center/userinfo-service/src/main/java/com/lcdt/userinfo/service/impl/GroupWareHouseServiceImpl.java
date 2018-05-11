package com.lcdt.userinfo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.userinfo.dao.TUserGroupWarehouseRelationMapper;
import com.lcdt.userinfo.localservice.GroupWareHouseService;
import com.lcdt.userinfo.model.TUserGroupWarehouseRelation;
import com.lcdt.userinfo.service.UserGroupService;
import com.lcdt.warehouse.entity.Warehouse;
import com.lcdt.warehouse.rpc.WarehouseRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    public List<TUserGroupWarehouseRelation> notAddUserGroupWareHouse(Long companyId,Long groupId) {
        List<TUserGroupWarehouseRelation> tUserGroupWarehouseRelations = dao.selectNotAddUserGroupWareHouse(groupId, companyId);
        if (tUserGroupWarehouseRelations == null) {
            return new ArrayList<TUserGroupWarehouseRelation>();
        }
        return tUserGroupWarehouseRelations;
    }

    @Reference
    WarehouseRpcService warehouseRpcService;

    @Autowired
    UserGroupService userGroupService;

    public TUserGroupWarehouseRelation addWareHouseRelation(Long groupId,Long companyId,Long wareHouseId){
        Warehouse warehouse = warehouseRpcService.selectByPrimaryKey(wareHouseId);
        if (!warehouse.getCompanyId().equals(companyId)) {
            return null;
        }

        TUserGroupWarehouseRelation relation = new TUserGroupWarehouseRelation();
        relation.setWareHouseId(wareHouseId);
        relation.setCreateDate(new Date());
        relation.setGroupId(groupId);
        relation.setCompanyId(companyId);

        dao.insert(relation);
        return relation;
    }

    /**
     * 移除 仓库 组关系
     */
    public void removeWareHouseGroupRelation(Long relationId){
        dao.deleteByPrimaryKey(relationId);
    }

}
