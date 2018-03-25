package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.DriverVehicleAuth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DriverVehicleAuthMapper {
    int deleteByPrimaryKey(Long authId);

    int insert(DriverVehicleAuth record);

    DriverVehicleAuth selectByPrimaryKey(Long authId);

    List<DriverVehicleAuth> selectAll();

    int updateByPrimaryKey(DriverVehicleAuth record);

    List<DriverVehicleAuth> selectByDriverId(Long driverId);

    List<DriverVehicleAuth> selectVehicleNumExist(DriverVehicleAuth auth);

    void updateDefault(@Param("driverId") Long driverId);
}