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
}
