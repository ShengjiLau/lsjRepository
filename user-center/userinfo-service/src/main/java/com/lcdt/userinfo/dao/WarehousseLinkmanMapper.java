package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.WarehouseLinkman;

import java.util.List;

public interface WarehousseLinkmanMapper {
    int deleteByPrimaryKey(Long whLinkmanId);

    int insert(WarehouseLinkman record);

    WarehouseLinkman selectByPrimaryKey(Long whLinkmanId);

    List<WarehouseLinkman> selectAll();

    int updateByPrimaryKey(WarehouseLinkman record);
}