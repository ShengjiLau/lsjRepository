package com.lcdt.userinfo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dao.DriverMapper;
import com.lcdt.userinfo.dao.DriverVehicleAuthMapper;
import com.lcdt.userinfo.dto.DriverDto;
import com.lcdt.userinfo.model.Driver;
import com.lcdt.userinfo.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-14
 */
@Service
@Transactional
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverMapper driverMapper;

    @Autowired
    private DriverVehicleAuthMapper driverVehicleAuthMapper;

    @Override
    public int addDriver(Driver driver) {
        if(!isExistDriver(driver.getDriverPhone())){
            return driverMapper.insert(driver);
        }
        return 0;
    }

    @Override
    public int modifyDriver(Driver driver) {
        if(!isExistDriver(driver.getDriverPhone())){
            return driverMapper.updateByPrimaryKey(driver);
        }
        return 0;
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
        if (null == driverMapper.selectByDriverPhone(driverPhone)) {
            return false;
        }
        return true;
    }

    @Override
    public PageInfo<List<DriverDto>> driverListForManager(DriverDto driverDto) {
        PageHelper.startPage(driverDto.getPageNum(), driverDto.getPageSize());
        List<DriverDto> driverList = driverMapper.selectForManager(driverDto);
        PageInfo page = new PageInfo(driverList);
        List<Long> longList = new ArrayList<>();
        for (DriverDto dto : driverList) {
            longList.add(dto.getUserId());
        }
        List<Map<String, ?>> mapList = driverVehicleAuthMapper.selectByDriverIds(longList);
        for (Map map : mapList) {
            for (int i =0;i<driverList.size();i++) {
                DriverDto dto = driverList.get(i);
                if (dto.getUserId() == map.get("driver_id")) {
                    dto.setVehicleNum(map.get("vehicle_num").toString());
                } else if (i == driverList.size() - 1) {
                    dto.setVehicleNum("0");
                }
            }
        }
        return page;
    }
}
