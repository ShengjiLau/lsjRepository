package com.lcdt.warehouse.mapper;

import com.lcdt.warehouse.entity.WarehouseLoc;

import java.util.List;
import java.util.Map;

public interface WarehousseLocMapper {
    int deleteByPrimaryKey(Long whLocId);

    int updateIsDeleteByPrimaryKey(Long whLocId);

    int insert(WarehouseLoc record);

    WarehouseLoc selectByPrimaryKey(Long whLocId);

    List<WarehouseLoc> selectAll();

    int updateByPrimaryKey(WarehouseLoc record);

    List<WarehouseLoc> selectByCondition(Map map);

    int selectByCode(String code);
}