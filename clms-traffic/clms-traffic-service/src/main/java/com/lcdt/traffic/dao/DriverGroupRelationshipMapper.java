package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.DriverGroupRelationship;
import java.util.List;

public interface DriverGroupRelationshipMapper {
    int deleteByPrimaryKey(Long dgrId);

    int insert(DriverGroupRelationship record);

    DriverGroupRelationship selectByPrimaryKey(Long dgrId);

    List<DriverGroupRelationship> selectAll();

    int updateByPrimaryKey(DriverGroupRelationship record);

    int deleteByOwnDriverId(Long ownDriverId, Long companyId);

    int insertBatch(List<DriverGroupRelationship> driverGroupRelationshipList);

    /**
     * 查询分组下是否存在关联的司机
     * @param driverGroupId
     * @param companyId
     * @return
     */
    int selectByDriverGroupId(Long driverGroupId,Long companyId);


    /***
     * 根据司机ID获取相应的组关系
     * @param arg1
     * @return
     */
    List<DriverGroupRelationship> selectByDriverId(Long arg1);
}