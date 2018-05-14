package com.lcdt.userinfo.localservice;

import com.lcdt.userinfo.model.TUserGroupWarehouseRelation;

import java.util.List;

public interface GroupWareHouseService {

    List<TUserGroupWarehouseRelation> addedUserGroupWareHouse(Long companyId,Long groupId);

    List<TUserGroupWarehouseRelation> notAddUserGroupWareHouse(Long companyId,Long groupId);

    TUserGroupWarehouseRelation addWareHouseRelation(Long groupId,Long userId,Long companyId,Long wareHouseId);

    void removeWareHouseGroupRelation(Long relationId);
}
