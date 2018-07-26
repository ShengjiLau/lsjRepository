package com.lcdt.warehouse.mapper;

import com.lcdt.warehouse.dto.WarehouseDto;
import com.lcdt.warehouse.entity.Warehouse;
import org.apache.ibatis.annotations.Param;

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

    List<Warehouse> selectNotInWhIds(Map map);

    int selectWarehouse(Warehouse warehouse);

    List<Warehouse> selectByGroupIdAndCmpId(Warehouse warehouse);
}