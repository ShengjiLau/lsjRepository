package com.lcdt.userinfo.service;

import com.lcdt.userinfo.model.Driver;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-14
 */
public interface DriverService {

    int addDriver(Driver driver);

    List<Driver> getGpsInfo(List<String> driverPhoneList);

    /**
     * 修改定位状态（0 - 未开通  1 - 待激活 2 - 已激活）
     * @param driver
     * @return
     */
    int modGpsStatus(Driver driver);

    int updateLocation(Driver driver);

    boolean isExistDriver(String driverPhone);
}
