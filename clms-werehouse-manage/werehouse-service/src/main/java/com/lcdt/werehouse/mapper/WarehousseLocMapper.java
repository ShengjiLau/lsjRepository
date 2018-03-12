package com.lcdt.werehouse.mapper;


import com.lcdt.werehouse.entity.WarehouseLoc;

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
}