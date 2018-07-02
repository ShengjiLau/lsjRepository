package com.lcdt.warehouse.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.warehouse.mapper.InWarehouseOrderMapper;
import com.lcdt.warehouse.rpc.WarehouseClearDataRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lyqishan on 2018/7/2
 */

@Service
public class WarehouseClearDataRpcServiceImpl implements WarehouseClearDataRpcService {
    @Autowired
    InWarehouseOrderMapper inWarehouseOrderMapper;

    @Override
    public void clearWarehouseData(Long companyId) {
        inWarehouseOrderMapper.clearWarehouseData(companyId);
    }
}
