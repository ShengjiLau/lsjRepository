package com.lcdt.werehouse.mapper;

import com.lcdt.werehouse.entity.WarehouseLinkman;

import java.util.List;
import java.util.Map;

public interface WarehousseLinkmanMapper {
    int deleteByPrimaryKey(Long whLinkmanId);

    int updateIsDeleteByPrimaryKey(Long whLinkmanId);

    int insert(WarehouseLinkman record);

    WarehouseLinkman selectByPrimaryKey(Long whLinkmanId);

    List<WarehouseLinkman> selectAll();

    int updateByPrimaryKey(WarehouseLinkman record);

    List<WarehouseLinkman> selectByCondition(Map map);

    int updateIsDefaultByPrimaryKey(WarehouseLinkman record);

    int updateIsDefaultByWhId(Long whId);

    List<WarehouseLinkman> selectByWhId(Map map);
}