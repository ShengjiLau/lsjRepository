package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.DriverAndGroup;
import com.lcdt.traffic.model.DriverGroup;
import com.lcdt.traffic.web.dto.DriverGroupDto;
import com.lcdt.traffic.web.dto.DriverGroupDto2;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-25
 */
public interface DriverGroupService {

    int addDriverGroup(DriverGroup driverGroup);

    int modDriverGroup(DriverGroup driverGroup);

    int delDriverGroup(DriverGroup driverGroup);

    List<DriverGroup> selectAll(Long companyId);

    List<DriverGroupDto> selectRelationship(Long companyId);

    PageInfo<List<DriverAndGroup>> selectDriverAndGroup(Long companyId, PageInfo pageInfo);

    /**
     * 根据groupIds获取组下所有司机信息（带分组信息）
     * @param companyId
     * @param driverGroupId
     * @return
     */
    List<DriverGroupDto2> driverListByGroupId2(Long companyId, String driverGroupId);
}
