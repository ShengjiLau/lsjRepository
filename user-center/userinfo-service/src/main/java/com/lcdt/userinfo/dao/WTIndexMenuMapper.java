package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.WTIndexMenu;

import java.util.List;

public interface WTIndexMenuMapper {
    int deleteByPrimaryKey(Long menuId);

    int insert(WTIndexMenu record);

    WTIndexMenu selectByPrimaryKey(Long menuId);

    List<WTIndexMenu> selectAll();

    int updateByPrimaryKey(WTIndexMenu record);
}