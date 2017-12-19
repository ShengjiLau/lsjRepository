package com.lcdt.traffic.service;

import com.lcdt.traffic.web.dto.OwnVehicleDto;

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
     * @param ownVehicleDto
     * @return
     */
    int modVehicle(OwnVehicleDto ownVehicleDto);

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
