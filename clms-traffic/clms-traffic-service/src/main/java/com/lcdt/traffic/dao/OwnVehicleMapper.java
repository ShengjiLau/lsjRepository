package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.OwnVehicle;
import java.util.List;

public interface OwnVehicleMapper {
    int deleteByPrimaryKey(Long ownVehicleId);

    int insert(OwnVehicle record);

    OwnVehicle selectByPrimaryKey(Long ownVehicleId);

    List<OwnVehicle> selectAll();

    int updateByPrimaryKey(OwnVehicle record);

    /**
     * 查询车牌持否存在 （查询的为count，0不存在）
     * @param vehicleNum
     * @return
     */
    int selectVehicleNum(String vehicleNum);
}