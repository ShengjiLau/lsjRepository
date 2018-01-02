package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.Driver;
import java.util.List;

public interface DriverMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(Driver record);

    Driver selectByPrimaryKey(Long userId);

    List<Driver> selectAll();

    int updateByPrimaryKey(Driver record);

    /**
     * 根据手机号码串批量查询定位状态及定位信息
     * @param driverPhoneList
     * @return
     */
    List<Driver> selectByPhone(List<String> driverPhoneList);

    /**
     * 根据手机号修改定位状态
     * @param record
     * @return
     */
    int updateGpsStatus(Driver record);

    int updateLocation(Driver driver);
}