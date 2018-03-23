package com.lcdt.traffic.rpc.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.lcdt.traffic.dao.OwnDriverMapper;
import com.lcdt.traffic.model.OwnDriver;
import com.lcdt.traffic.service.OwnDriverCompanyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class OwnDriverCompanyServiceImpl implements OwnDriverCompanyService{

    @Autowired
    OwnDriverMapper dao;

    @Override
    public List<OwnDriver> driverCompanys(Long driverId,Integer pageNo,Integer pageSize) {

        if (pageNo != null) {
            if (pageSize == null) {
                pageSize = 10;
            }
            PageHelper.startPage(pageNo, pageSize);
        }
        List<OwnDriver> ownDrivers = dao.selectByDriverId(driverId);
        return ownDrivers;
    }

    @Override
    public void removeDriverCompany(Long ownDriverId, Long companyId) {
        OwnDriver ownDriver = new OwnDriver();
        ownDriver.setCompanyId(companyId);
        ownDriver.setOwnDriverId(ownDriverId);
        dao.deleteByUpdate(ownDriver);
    }
}
