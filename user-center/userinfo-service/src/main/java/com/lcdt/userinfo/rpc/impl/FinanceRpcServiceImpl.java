package com.lcdt.userinfo.rpc.impl;

import com.lcdt.userinfo.dao.FeePropertyMapper;
import com.lcdt.userinfo.model.FeeProperty;
import com.lcdt.userinfo.rpc.FinanceRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by liz on 2018/4/11.
 */
public class FinanceRpcServiceImpl implements FinanceRpcService {
    @Autowired
    private FeePropertyMapper feePropertyMapper;
    @Override
    public List<FeeProperty> getFeePropertyList(Map m){
        List<FeeProperty> list = feePropertyMapper.selectByCondition(m);
        return list;
    }

    @Override
    public List<FeeProperty> selectByCondition(Map m) {
        List<FeeProperty> list = feePropertyMapper.selectByCondition(m);
        return list;
    }
}
