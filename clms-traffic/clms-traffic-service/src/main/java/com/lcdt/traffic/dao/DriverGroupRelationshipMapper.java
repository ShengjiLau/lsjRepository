package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.DriverGroupRelationship;
import java.util.List;

public interface DriverGroupRelationshipMapper {
    int deleteByPrimaryKey(Long driverGroupId);

    int insert(DriverGroupRelationship record);

    DriverGroupRelationship selectByPrimaryKey(Long driverGroupId);

    List<DriverGroupRelationship> selectAll();

    int updateByPrimaryKey(DriverGroupRelationship record);
}