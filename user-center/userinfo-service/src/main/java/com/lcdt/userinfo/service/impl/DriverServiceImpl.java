package com.lcdt.userinfo.service.impl;

import com.lcdt.userinfo.dao.DriverMapper;
import com.lcdt.userinfo.model.Driver;
import com.lcdt.userinfo.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-14
 */
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverMapper driverMapper;

    @Override
    public int addDriver(Driver driver) {
        return driverMapper.insert(driver);
    }
}
