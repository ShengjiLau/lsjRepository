package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.WarehousseLinkman;

import java.util.List;

public interface WarehousseLinkmanMapper {
    int deleteByPrimaryKey(Long whLinkmanId);

    int insert(WarehousseLinkman record);

    WarehousseLinkman selectByPrimaryKey(Long whLinkmanId);

    List<WarehousseLinkman> selectAll();

    int updateByPrimaryKey(WarehousseLinkman record);
}