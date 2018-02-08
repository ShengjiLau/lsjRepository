package com.lcdt.userinfo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.userinfo.dao.DriverMapper;
import com.lcdt.userinfo.model.Driver;
import com.lcdt.userinfo.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-14
 */
@Service
@Transactional
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverMapper driverMapper;

    @Override
    public int addDriver(Driver driver) {
        return driverMapper.insert(driver);
    }

    @Override
    public List<Driver> getGpsInfo(List<String> driverPhoneList) {
        return driverMapper.selectByPhone(driverPhoneList);
    }

    @Override
    public int modGpsStatus(Driver driver) {
        return driverMapper.updateGpsStatus(driver);
    }

    @Override
    public int updateLocation(Driver driver) {
        return driverMapper.updateLocation(driver);
    }

    @Override
    public boolean isExistDriver(String driverPhone) {
        if(null==driverMapper.selectByDriverPhone(driverPhone)){
            return false;
        }
        return true;
    }
}
