package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.Warehousse;

import java.util.List;

public interface WarehousseMapper {
    int deleteByPrimaryKey(Long whId);

    int insert(Warehousse record);

    Warehousse selectByPrimaryKey(Long whId);

    List<Warehousse> selectAll();

    int updateByPrimaryKey(Warehousse record);
}