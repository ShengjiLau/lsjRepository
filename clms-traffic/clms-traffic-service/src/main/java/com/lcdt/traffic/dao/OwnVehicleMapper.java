package com.lcdt.traffic.dao;

import com.lcdt.traffic.dto.BackstageVehicleDto;
import com.lcdt.traffic.dto.OwnVehicleDto;
import com.lcdt.traffic.model.OwnDriver;
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
     *
     * @param ownVehicle
     * @return
     */
    int selectVehicleNum(OwnVehicle ownVehicle);


    /**
     * 根据车牌查询车辆信息
     * @param ownVehicle
     * @return
     */
    OwnVehicle selectByVehicleNum(OwnVehicle ownVehicle);

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

    /**
     * 更新我的车辆里，司机账号相关的主键信息（即将user_id更新到vehicle_driver_id字段）
     * @param ownVehicle
     * @return
     */
    int updateDriverId(OwnVehicle ownVehicle);

    /**
     * 查询车牌持否存在 （查询的为count，0不存在）
     *
     * @param ownVehicle
     * @return
     */
    OwnVehicle selectAddWaybillVehicleNum(OwnVehicle ownVehicle);

    /**
     * 大后台查询车辆信息
     * @param dto
     * @return
     */
    List<OwnVehicle> selectForManage(BackstageVehicleDto dto);
}