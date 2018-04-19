package com.lcdt.userinfo.localservice;

import com.lcdt.userinfo.model.TUserGroupWarehouseRelation;

import java.util.List;

public interface GroupWareHouseService {

    List<TUserGroupWarehouseRelation> addedUserGroupWareHouse(Long companyId,Long groupId);

    List<TUserGroupWarehouseRelation> notAddUserGroupWareHouse();


}
