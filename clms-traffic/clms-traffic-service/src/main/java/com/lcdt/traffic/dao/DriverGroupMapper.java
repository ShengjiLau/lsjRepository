package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.DriverGroup;
import java.util.List;

public interface DriverGroupMapper {
    int deleteByPrimaryKey(Long driverGroupId);

    int insert(DriverGroup record);

    DriverGroup selectByPrimaryKey(Long driverGroupId);

    int deleteByUpdate(DriverGroup driverGroup);

    List<DriverGroup> selectAll(Long companyId);

    int updateByPrimaryKey(DriverGroup record);
}