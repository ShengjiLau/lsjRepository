package com.lcdt.traffic.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.traffic.dao.OwnDriverMapper;
import com.lcdt.traffic.model.OwnDriver;
import com.lcdt.traffic.service.TrafficRpc;
import com.lcdt.traffic.service.WaybillService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2018/1/12.
 */
@Service
public class TrafficRpcImpl implements TrafficRpc {

    @Autowired
    private WaybillService waybillService;

    @Autowired
    private OwnDriverMapper ownDriverMapper;

    @Override
    public void waybillPositionTimer(Map map) {
        waybillService.queryWaybillListToPoPosition(map);
    }

    public List<OwnDriver> driverListByGroupId(Long companyId, String groupIds){
        return ownDriverMapper.selectDriverByGroupIds(companyId,groupIds);
    }
}
