package com.lcdt.traffic.dao;

import com.lcdt.traffic.dto.DriverGroupDto;
import com.lcdt.traffic.dto.DriverGroupDto2;
import com.lcdt.traffic.model.DriverAndGroup;
import com.lcdt.traffic.model.DriverGroup;

import java.util.List;
import java.util.Map;

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
     * @param map
     * @return
     */
    List<DriverGroupDto2> selectDriverByGroupIds2(Map<String,Object> map);
}