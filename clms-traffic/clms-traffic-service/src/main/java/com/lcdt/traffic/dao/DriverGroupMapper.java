package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.DriverGroup;
import java.util.List;

public interface DriverGroupMapper {
    int deleteByPrimaryKey(Long driverGroupId);

    int insert(DriverGroup record);

    DriverGroup selectByPrimaryKey(Long driverGroupId);

    List<DriverGroup> selectAll();

    int updateByPrimaryKey(DriverGroup record);
}