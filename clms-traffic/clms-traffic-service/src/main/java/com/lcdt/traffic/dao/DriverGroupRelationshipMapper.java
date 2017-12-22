package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.DriverGroupRelationship;
import java.util.List;

public interface DriverGroupRelationshipMapper {
    int deleteByPrimaryKey(Long dgrId);

    int insert(DriverGroupRelationship record);

    DriverGroupRelationship selectByPrimaryKey(Long dgrId);

    List<DriverGroupRelationship> selectAll();

    int updateByPrimaryKey(DriverGroupRelationship record);
}