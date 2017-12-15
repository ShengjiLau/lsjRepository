package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.OwnDriver;
import java.util.List;

public interface OwnDriverMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(OwnDriver record);

    OwnDriver selectByPrimaryKey(Long userId);

    List<OwnDriver> selectAll();

    int updateByPrimaryKey(OwnDriver record);
}