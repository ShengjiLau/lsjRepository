package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.DriverVehicleAuth;
import com.lcdt.userinfo.web.controller.api.admin.dto.DriverQueryDto;
import com.lcdt.userinfo.web.controller.api.admin.dto.VehicleAuthDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DriverVehicleAuthMapper {
    int deleteByPrimaryKey(Long authId);

    int insert(DriverVehicleAuth record);

    DriverVehicleAuth selectByPrimaryKey(Long authId);

    List<DriverVehicleAuth> selectAll();

    int updateByPrimaryKey(DriverVehicleAuth record);

    List<DriverVehicleAuth> selectByDriverId(Long driverId);

    List<DriverVehicleAuth> selectVehicleNumExist(DriverVehicleAuth auth);

    void updateDefault(@Param("driverId") Long driverId);

    List<DriverVehicleAuth> selectByDriverQueryDto(DriverQueryDto driverQueryDto);

    List<Map<String,?>> selectByDriverIds(List<Long> driverIds);

    int updateAuthStatus(VehicleAuthDto auth);

}