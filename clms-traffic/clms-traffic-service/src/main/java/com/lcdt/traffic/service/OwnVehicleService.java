package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.OwnVehicle;
import com.lcdt.traffic.web.dto.OwnVehicleDto;
import com.lcdt.userinfo.model.Driver;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-13
 */
public interface OwnVehicleService {

    /**
     * 新增车辆
     *
     * @param ownVehicleDto
     * @return
     */
    int addVehicle(OwnVehicleDto ownVehicleDto);

    /**
     * 修改更新车辆
     *
     * @param ownVehicleDto
     * @return
     */
    int modVehicle(OwnVehicleDto ownVehicleDto);

    /**
     * 删除车辆
     *
     * @param ownVehicleDto
     * @return
     */
    int delVehicle(OwnVehicleDto ownVehicleDto);

    /**
     * 车辆列表
     *
     * @return
     */
    PageInfo<List<OwnVehicle>> ownVehicleList(OwnVehicle ownVehicle, PageInfo pageInfo);

    /**
     * 获取定位信息
     * @param driverPhoneList
     * @return
     */
    List<Driver> getGpsInfo(List<String> driverPhoneList);
}
