package com.lcdt.traffic.service;

import com.lcdt.traffic.model.DriverGroup;

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

}
