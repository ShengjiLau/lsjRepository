package com.lcdt.traffic.service.impl;

import com.lcdt.traffic.dao.DriverGroupMapper;
import com.lcdt.traffic.model.DriverGroup;
import com.lcdt.traffic.service.DriverGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-25
 */
@Service
public class DriverGroupServiceImpl implements DriverGroupService {

    @Autowired
    private DriverGroupMapper driverGroupMapper;

    @Override
    public int addDriverGroup(DriverGroup driverGroup) {
        return driverGroupMapper.insert(driverGroup);
    }

    @Override
    public int modDriverGroup(DriverGroup driverGroup) {
        return driverGroupMapper.updateByPrimaryKey(driverGroup);
    }

    @Override
    public int delDriverGroup(DriverGroup driverGroup) {
        return driverGroupMapper.deleteByUpdate(driverGroup);
    }

    @Override
    public List<DriverGroup> selectAll(Long companyId) {
        return driverGroupMapper.selectAll(companyId);
    }
}
