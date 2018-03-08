package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.DriverAndGroup;
import com.lcdt.traffic.model.DriverGroup;
import com.lcdt.traffic.web.dto.DriverGroupDto;
import com.lcdt.traffic.web.dto.DriverGroupDto2;

import java.util.List;

public interface DriverGroupMapper {
    int deleteByPrimaryKey(Long driverGroupId);

    int insert(DriverGroup record);

    DriverGroup selectByPrimaryKey(Long driverGroupId);

    int deleteByUpdate(DriverGroup driverGroup);

    List<DriverGroup> selectAll(Long companyId);

    /**
     * 查询分组是否存在（查询的为count，0不存在）
     * @param companyId
     * @param groupName
     * @return
     */
    int selectByGroupName(Long companyId,String groupName,Long driverGroupId);

    int updateByPrimaryKey(DriverGroup record);

    List<DriverGroupDto> selectRelationship(Long companyId);

    List<DriverAndGroup> selectDriverAndGroup(Long companyId);

    /**
     * 根据groupIds获取司机信息
     * @param companyId
     * @param driverGroupId
     * @return
     */
    List<DriverGroupDto2> selectDriverByGroupIds2(Long companyId, String driverGroupId);
}