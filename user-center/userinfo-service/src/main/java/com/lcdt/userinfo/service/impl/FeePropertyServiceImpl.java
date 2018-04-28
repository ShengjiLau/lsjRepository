package com.lcdt.userinfo.service.impl;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.service.FeePropertyRpcService;
import com.lcdt.userinfo.dao.FeePropertyMapper;
import com.lcdt.userinfo.model.FeeProperty;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.FeePropertyService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    private FeePropertyRpcService feePropertyRpcService;

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
        int result = feePropertyMapper.updateByPrimaryKeySelective(feeProperty);
        return result;
    }

    @Override
    public int modifyFeePropertyIsDelete(Long proId) {
        int result =  feePropertyMapper.updateIsDeleteByPrimaryKey(proId);//=1
        return result;
    }
    @Override
    public List<FeeProperty> getFeePropertyList(Map m){
        List<FeeProperty> list = feePropertyMapper.selectByCondition(m);
        return list;
    }
    @Override
    public int initFeeProperty(User user, Long companyId) {
        List<FeeProperty> feePropertyList = new ArrayList<>();

        //运输'运费'应收默认显示
        FeeProperty feeProperty1 = new FeeProperty();
        feeProperty1.setType((short)0);//运输
        feeProperty1.setName("运费");
        feeProperty1.setIsReceivable((short)0);//应收
        feeProperty1.setIsShow((short)0);//默认显示
        feeProperty1.setCompanyId(companyId);
        feeProperty1.setOperatorId(user.getUserId());
        feeProperty1.setOperatorName(user.getRealName());
        feeProperty1.setCreateDate(new Date());
        feeProperty1.setIsDeleted((short)0);
        feePropertyList.add(feeProperty1);

        //运输'运费'应付默认显示
        FeeProperty feeProperty2 = new FeeProperty();
        BeanUtils.copyProperties(feeProperty1, feeProperty2);
        feeProperty2.setIsReceivable((short)1);//应付
        feePropertyList.add(feeProperty2);

        //仓储'运费'应收默认显示
        FeeProperty feeProperty3 = new FeeProperty();
        BeanUtils.copyProperties(feeProperty1, feeProperty3);
        feeProperty3.setType((short)1);//仓储
        feePropertyList.add(feeProperty3);

        //仓储'运费'应收默认显示
        FeeProperty feeProperty4 = new FeeProperty();
        BeanUtils.copyProperties(feeProperty3, feeProperty4);
        feeProperty4.setIsReceivable((short)1);//应付
        feePropertyList.add(feeProperty4);

        int result = feePropertyMapper.insertBatch(feePropertyList);
        return result;
    }
}
