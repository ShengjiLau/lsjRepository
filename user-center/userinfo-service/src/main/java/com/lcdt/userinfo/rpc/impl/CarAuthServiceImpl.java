package com.lcdt.userinfo.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.lcdt.userinfo.dao.DriverVehicleAuthMapper;
import com.lcdt.userinfo.model.DriverVehicleAuth;
import com.lcdt.userinfo.rpc.CarAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarAuthServiceImpl implements CarAuthService{

    @Autowired
    DriverVehicleAuthMapper dao;

    @Transactional
    @Override
    public DriverVehicleAuth addVehicleAuth(DriverVehicleAuth auth) {
        auth.setAuthStatus("0");
        dao.insert(auth);
        return auth;
    }

    @Override
    public List<DriverVehicleAuth> vehicleList(Long driverId, Integer pageNo, Integer pageSize) {
        if (pageNo != null) {
            if (pageSize == null) {
                pageSize = 10;
            }
            PageHelper.startPage(pageNo, pageSize);
        }
        List<DriverVehicleAuth> driverVehicleAuths = dao.selectByDriverId(driverId);
        return driverVehicleAuths;
    }

    @Override
    public DriverVehicleAuth selectById(Long authId) {
        DriverVehicleAuth driverVehicleAuth = dao.selectByPrimaryKey(authId);
//        List<DriverVehicleAuth> driverVehicleAuths = dao.selectByDriverId(authId);

        return driverVehicleAuth;
    }

    @Override
    public DriverVehicleAuth updateVehicleAuth(DriverVehicleAuth auth) {
        dao.updateByPrimaryKey(auth);
        return auth;
    }


}
