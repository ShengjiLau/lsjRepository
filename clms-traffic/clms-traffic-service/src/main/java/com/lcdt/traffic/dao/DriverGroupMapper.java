package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.DriverGroup;
import java.util.List;

public interface DriverGroupMapper {
    int deleteByPrimaryKey(Long dgId);

    int insert(DriverGroup record);

    DriverGroup selectByPrimaryKey(Long dgId);

    List<DriverGroup> selectAll();

    int updateByPrimaryKey(DriverGroup record);
}