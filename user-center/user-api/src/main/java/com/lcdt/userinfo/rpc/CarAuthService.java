package com.lcdt.userinfo.rpc;

import com.lcdt.userinfo.model.DriverVehicleAuth;

import java.util.List;

public interface CarAuthService {

    DriverVehicleAuth addVehicleAuth(DriverVehicleAuth auth);

    List<DriverVehicleAuth> vehicleList(Long driverId,Integer pageNo,Integer pageSize);

    DriverVehicleAuth selectById(Long authId);

    DriverVehicleAuth updateVehicleAuth(DriverVehicleAuth auth);
}
