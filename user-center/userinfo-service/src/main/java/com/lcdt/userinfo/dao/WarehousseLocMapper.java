package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.WarehouseLoc;

import java.util.List;

public interface WarehousseLocMapper {
    int deleteByPrimaryKey(Long whLocId);

    int insert(WarehouseLoc record);

    WarehouseLoc selectByPrimaryKey(Long whLocId);

    List<WarehouseLoc> selectAll();

    int updateByPrimaryKey(WarehouseLoc record);
}