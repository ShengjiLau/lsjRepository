package com.lcdt.traffic.service;

import com.lcdt.traffic.model.OwnDriver;

import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2018/1/12.
 */
public interface TrafficRpc {

    void waybillPositionTimer(Map map);

    /**
     * 根据groupIds获取组下所有司机信息
     * @param companyId
     * @param groupIds
     * @return
     */
    List<OwnDriver> driverListByGroupId(Long companyId, String groupIds);
}
