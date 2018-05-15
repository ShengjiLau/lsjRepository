package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.TUserGroupWarehouseRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TUserGroupWarehouseRelationMapper {
    int deleteByPrimaryKey(Long relationId);

    int insert(TUserGroupWarehouseRelation record);

    TUserGroupWarehouseRelation selectByPrimaryKey(Long relationId);

    List<TUserGroupWarehouseRelation> selectAll();

    int updateByPrimaryKey(TUserGroupWarehouseRelation record);

    List<TUserGroupWarehouseRelation> selectAddedUserGroupWareHouse(@Param("groupId")Long groupId,@Param("companyId")Long companyId);

    List<TUserGroupWarehouseRelation> selectNotAddUserGroupWareHouse(@Param("groupId")Long groupId,@Param("companyId")Long companyId);

    int deleteBatch(Long wareHouseId);

    String selectGroupNamesByGroupIds(Map map);
}