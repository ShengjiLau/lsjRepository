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
import org.tl.commons.util.StringUtility;

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

    public TUserGroupWarehouseRelation addWareHouseRelation(Long groupId,Long userId,Long companyId,Long wareHouseId){
        Warehouse warehouse = warehouseRpcService.selectByPrimaryKey(wareHouseId);
        if (!warehouse.getCompanyId().equals(companyId)) {
            return null;
        }
        if(StringUtility.isNotEmpty(warehouse.getGroupIds())){
            warehouse.setGroupIds(warehouse.getGroupIds()+","+groupId);
        }else{
            warehouse.setGroupIds(""+groupId);
        }
        warehouseRpcService.modifyWarehouse(warehouse);
        TUserGroupWarehouseRelation relation = new TUserGroupWarehouseRelation();
        relation.setUserId(userId);
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
        TUserGroupWarehouseRelation relation = dao.selectByPrimaryKey(relationId);
        if(relation != null){
            Warehouse warehouse = warehouseRpcService.selectByPrimaryKey(relation.getWareHouseId());
            if (StringUtility.isNotEmpty(warehouse.getGroupIds())){
                String[] groupIdArr = warehouse.getGroupIds().split(",");
                StringBuffer newGroupIds = new StringBuffer();
                for(int i=0; i<groupIdArr.length; i++){
                    if(Long.parseLong(groupIdArr[i]) != relation.getGroupId()){
                        newGroupIds.append(groupIdArr[i]+",");
                    }
                }
                if(newGroupIds.length() > 0){
                    warehouse.setGroupIds(newGroupIds.substring(0,newGroupIds.length()-1));
                }else{
                    warehouse.setGroupIds(newGroupIds.toString());
                }
                warehouseRpcService.modifyWarehouse(warehouse);
            }
        }
        dao.deleteByPrimaryKey(relationId);
    }

}
