package com.lcdt.traffic.service;

import com.lcdt.traffic.dto.WaybillParamsDto;
import com.lcdt.traffic.model.OwnDriver;
import com.lcdt.traffic.model.OwnVehicle;
import com.lcdt.userinfo.model.UserCompRel;

import java.util.Map;

/**
 * Created by yangbinq on 2018/1/12.
 */
public interface TrafficRpc {

    void waybillPositionTimer(Map map);

    /**
     * 新增车辆
     * @param ownVehicle
     * @return
     */
    OwnVehicle addVehicle(OwnVehicle ownVehicle);

    /**
     * 新增司机
     * @param ownDriver
     * @return
     */
    OwnDriver addDriver(OwnDriver ownDriver);










}
