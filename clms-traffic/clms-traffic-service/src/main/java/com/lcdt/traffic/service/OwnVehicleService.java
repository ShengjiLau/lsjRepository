package com.lcdt.traffic.service;

import com.lcdt.traffic.dto.OwnVehicleDto;
import com.lcdt.traffic.model.OwnVehicle;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-13
 */
public interface OwnVehicleService {

    /**
     * 新增车辆
     * @param ownVehicleDto
     * @return
     */
    int addVehicle(OwnVehicleDto ownVehicleDto);

    /**
     * 修改更新车辆
     * @param ownVehicle
     * @return
     */
    int modVhehicle(OwnVehicle ownVehicle);

    /**
     * 删除车辆
     * @param ownVehicleId
     * @return
     */
    int delVehicle(Long ownVehicleId);

    /**
     * 车辆列表
     * @return
     */
    List<OwnVehicleDto> ownVehicleDtoList();
}
