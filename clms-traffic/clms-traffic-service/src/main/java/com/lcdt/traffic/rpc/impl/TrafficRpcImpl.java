package com.lcdt.traffic.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.traffic.service.TrafficRpc;
import com.lcdt.traffic.service.WaybillService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by yangbinq on 2018/1/12.
 */
@Service
public class TrafficRpcImpl implements TrafficRpc {

    @Autowired
    private WaybillService waybillService;

    @Override
    public void waybillPositionTimer(Map map) {
        waybillService.queryWaybillListToPoPosition(map);
    }


}
