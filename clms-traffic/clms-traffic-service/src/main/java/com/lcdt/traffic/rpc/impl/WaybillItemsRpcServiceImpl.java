package com.lcdt.traffic.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.traffic.dao.WaybillItemsMapper;
import com.lcdt.traffic.service.WaybillItemsRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lyqishan on 2018/6/6
 */
@Service
@Transactional
public class WaybillItemsRpcServiceImpl implements WaybillItemsRpcService {

    @Autowired
    WaybillItemsMapper waybillItemsMapper;

    @Override
    public int deleteWaybillItems(Long companyId, Long waybillItemsId) {
        return waybillItemsMapper.deleteByPrimaryKey(companyId,waybillItemsId);
    }
}
