package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.OwnVehicle;
import com.lcdt.traffic.web.dto.OwnVehicleDto;

import java.util.List;

public interface OwnVehicleMapper {
    int deleteByPrimaryKey(Long ownVehicleId);

    int insert(OwnVehicle record);

    OwnVehicle selectByPrimaryKey(Long ownVehicleId);

    List<OwnVehicle> selectAll();

    int updateByPrimaryKey(OwnVehicle record);

    /**
     * 查询车牌持否存在 （查询的为count，0不存在）
     *
     * @param vehicleNum
     * @return
     */
    int selectVehicleNum(String vehicleNum);

    /**
     * 删除车辆（实际为更新is_deteted字段）
     *
     * @param ownVehicle
     * @return
     */
    int deleteByUpdate(OwnVehicle ownVehicle);

    /**
     * 按条件查询
     *
     * @param ownVehicle
     * @return
     */
    List<OwnVehicle> selectByCondition(OwnVehicle ownVehicle);

    /**
     * 查询车辆详情
     * @param ownVehicleId
     * @return
     */
    OwnVehicleDto selectDetail(Long ownVehicleId, Long companyId);

}