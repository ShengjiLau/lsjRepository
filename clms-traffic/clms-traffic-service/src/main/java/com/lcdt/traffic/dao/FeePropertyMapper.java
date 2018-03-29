package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.FeeProperty;

import java.util.List;
import java.util.Map;

public interface FeePropertyMapper {

    int deleteByPrimaryKey(Long proId);

    int insert(FeeProperty record);

    FeeProperty selectByPrimaryKey(Long proId);

    int updateByPrimaryKey(FeeProperty record);

    List<FeeProperty> selectByCondition(Map map);

    int updateIsDeleteByPrimaryKey(Long proId);

    int getFeePropertyInfo(Map m);
}