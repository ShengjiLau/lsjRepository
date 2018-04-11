package com.lcdt.userinfo.service.impl;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.FeeFlow;
import com.lcdt.traffic.service.TrafficRpc;
import com.lcdt.userinfo.dao.FeePropertyMapper;
import com.lcdt.userinfo.model.FeeProperty;
import com.lcdt.userinfo.service.FeePropertyService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by liz on 2018/3/29.
 */
@Service
public class FeePropertyServiceImpl implements FeePropertyService {

    @Autowired
    private FeePropertyMapper feePropertyMapper;
    @Reference
    private TrafficRpc trafficRpc;

    @Override
    public PageInfo feePropertyList(Map m) {
        List<FeeProperty> list = feePropertyMapper.selectByCondition(m);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public int addFeeProperty(FeeProperty feeProperty) {
        int result = feePropertyMapper.insert(feeProperty);
        return result;
    }

    @Override
    public int modifyFeeProperty(FeeProperty feeProperty) {
        int result = feePropertyMapper.updateByPrimaryKey(feeProperty);
        return result;
    }

    @Override
    public int modifyFeePropertyIsDelete(Long proId) {
        List<FeeFlow> feeFlowList = trafficRpc.selectFlowsByProId(proId);
        int result = 0;
        if(feeFlowList != null && feeFlowList.size() > 0){
             result = 2;
        }else {
            result = feePropertyMapper.updateIsDeleteByPrimaryKey(proId);//=1
        }
        return result;
    }
    @Override
    public List<FeeProperty> getFeePropertyList(Map m){
        List<FeeProperty> list = feePropertyMapper.selectByCondition(m);
        return list;
    }
}
