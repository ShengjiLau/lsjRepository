package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.DriverVehicleAuth;

import java.util.List;

public interface DriverVehicleAuthMapper {
    int deleteByPrimaryKey(Long authId);

    int insert(DriverVehicleAuth record);

    DriverVehicleAuth selectByPrimaryKey(Long authId);

    List<DriverVehicleAuth> selectAll();

    int updateByPrimaryKey(DriverVehicleAuth record);
}