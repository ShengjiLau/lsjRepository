package com.lcdt.traffic.service.impl;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.FeePropertyMapper;
import com.lcdt.traffic.model.FeeProperty;
import com.lcdt.traffic.service.FeePropertyService;
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
        int result = feePropertyMapper.updateIsDeleteByPrimaryKey(proId);
        return result;
    }
    @Override
    public List<FeeProperty> getFeePropertyList(Map m){
        List<FeeProperty> list = feePropertyMapper.selectByCondition(m);
        return list;
    }
}
