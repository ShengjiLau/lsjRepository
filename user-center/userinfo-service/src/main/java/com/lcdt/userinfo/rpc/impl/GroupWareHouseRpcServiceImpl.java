package com.lcdt.userinfo.rpc.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.userinfo.dao.GroupMapper;
import com.lcdt.userinfo.dao.TUserGroupWarehouseRelationMapper;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.TUserGroupWarehouseRelation;
import com.lcdt.userinfo.rpc.GroupWareHouseRpcService;
import com.lcdt.warehouse.entity.Warehouse;
import com.lcdt.warehouse.rpc.WarehouseRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Created by liz on 2018/5/14.
 */
@Service
public class GroupWareHouseRpcServiceImpl implements GroupWareHouseRpcService{
    @Autowired
    TUserGroupWarehouseRelationMapper tUserGroupWarehouseRelationMapper;
    @Reference
    WarehouseRpcService warehouseRpcService;
    @Autowired
    GroupMapper groupMapper;

    @Override
    public boolean addWareHouseRelationBatch(String groupIds, Long userId, Long companyId, Long wareHouseId){
        try{
            Warehouse warehouse = warehouseRpcService.selectByPrimaryKey(wareHouseId);
            if (!warehouse.getCompanyId().equals(companyId)) {
                return false;
            }
            String[] groupIdArr = groupIds.split(",");
            for(String groupId : groupIdArr){
                TUserGroupWarehouseRelation relation = new TUserGroupWarehouseRelation();
                relation.setUserId(userId);
                relation.setWareHouseId(wareHouseId);
                relation.setCreateDate(new Date());
                relation.setGroupId(Long.parseLong(groupId));
                relation.setCompanyId(companyId);
                tUserGroupWarehouseRelationMapper.insert(relation);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int deleteWareHouseRelationBatch(Long wareHouseId) {
        int result = tUserGroupWarehouseRelationMapper.deleteBatch(wareHouseId);
        return result;
    }

    @Override
    public String selectGroupNamesByGroupIds(Map map){
        String groupNames = tUserGroupWarehouseRelationMapper.selectGroupNamesByGroupIds(map);
        return groupNames;
    }

    @Override
    public Group selectByGroupId(Long groupId){
        return groupMapper.selectByPrimaryKey(groupId);
    }

}
