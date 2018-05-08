package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.Warehouse;
import com.lcdt.userinfo.web.dto.WarehouseDto;

import java.util.List;
import java.util.Map;

public interface WarehousseMapper {
    int deleteByPrimaryKey(Long whId);

    int updateIsDeleteByPrimaryKey(Long whId);

    int insert(Warehouse record);

    Warehouse selectByPrimaryKey(Long whId);

    List<Warehouse> selectAll();

    int updateByPrimaryKey(Warehouse record);

    List<WarehouseDto> selectByCondition(Map map);

}