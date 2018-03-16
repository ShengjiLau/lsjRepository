package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.OwnDriver;
import com.lcdt.traffic.web.dto.OwnDriverDto;

import java.util.List;

public interface OwnDriverMapper {
    int deleteByPrimaryKey(Long ownDriverId);

    int insert(OwnDriver record);

    OwnDriver selectByPrimaryKey(Long ownDriverId);

    List<OwnDriver> selectAll();

    int updateByPrimaryKey(OwnDriver record);

    /**
     * 查询司机手机号否存在 （查询的为count，0不存在）
     *
     * @param ownDriver
     * @return
     */
    int selectDriverPhone(OwnDriver ownDriver);

    /**
     * 删除司机（实际为更新is_deteted字段）
     *
     * @param ownDriver
     * @return
     */
    int deleteByUpdate(OwnDriver ownDriver);

    /**
     * 按条件查询
     *
     * @param ownDriver
     * @return
     */
    List<OwnDriver> selectByCondition(OwnDriver ownDriver);

    /**
     * 查询司机详情
     * @param ownDriverId
     * @param companyId
     * @return
     */
    OwnDriverDto selectDetail(Long ownDriverId, Long companyId);

    /**
     * 根据groupIds获取司机信息
     * @param companyId
     * @param driverGroupId
     * @return
     */
    List<OwnDriver> selectDriverByGroupIds(Long companyId,String driverGroupId);

    /**
     * 更新我的司机里，司机相关的主键信息（即将user_id更新到driver_id字段）
     * @param ownDriver
     * @return
     */
    int updateDriverId(OwnDriver ownDriver);

}