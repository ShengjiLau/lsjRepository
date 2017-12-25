package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.DriverGroupRelationship;
import com.lcdt.traffic.model.OwnDriver;
import com.lcdt.traffic.web.dto.OwnDriverDto;
import com.lcdt.userinfo.model.Driver;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-21
 */
public interface OwnDriverService {

    /**
     * 新增车辆
     *
     * @param ownDriverDto
     * @return
     */
    int addDriver(OwnDriverDto ownDriverDto);

    /**
     * 修改更新车辆
     *
     * @param ownDriverDto
     * @return
     */
    int modDriver(OwnDriverDto ownDriverDto);

    /**
     * 删除车辆
     *
     * @param ownDriverDto
     * @return
     */
    int delDriver(OwnDriverDto ownDriverDto);

    /**
     * 车辆列表
     *
     * @return
     */
    PageInfo<List<OwnDriver>> ownDriverList(OwnDriver ownDriver, PageInfo pageInfo);

    /**
     * 车辆详情
     *
     * @param ownDriverId
     * @return
     */
    OwnDriverDto ownDriverDetail(Long ownDriverId, Long companyId);

    /**
     * 获取定位信息
     *
     * @param driverPhoneList
     * @return
     */
    List<Driver> getGpsInfo(List<String> driverPhoneList);

    int addGroupInfo(List<DriverGroupRelationship> driverGroupRelationshipList);
}
