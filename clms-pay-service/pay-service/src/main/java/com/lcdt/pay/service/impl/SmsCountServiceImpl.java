package com.lcdt.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.pay.dao.ProductCountLogMapper;
import com.lcdt.pay.model.CompanyServiceCount;
import com.lcdt.pay.rpc.CompanyServiceCountService;
import com.lcdt.pay.rpc.ProductCountLog;
import com.lcdt.pay.rpc.SmsCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SmsCountServiceImpl implements SmsCountService {

    @Autowired
    ProductCountLogMapper countLogMapper;

    @Autowired
    CompanyServiceCountService countService;

    @Transactional
    @Override
    public void reduceSmsCount(Long countLogId,  String productName,Integer reduceNum) {
        ProductCountLog productCountLog = countLogMapper.selectByPrimaryKey(countLogId);
        CompanyServiceCount companyServiceCount = countService.reduceCompanyProductCount(productCountLog.getCompanyId(), productName, reduceNum);
        productCountLog.setRemainNum(companyServiceCount.getProductServiceNum());
        countLogMapper.updateByPrimaryKey(productCountLog);
    }

    @Override
    public boolean checkSmsCount(Long companyId, Integer num) {
        return countService.checkCompanyProductCount(companyId, "sms_service", num);
    }


    @Override
    public boolean checkProductCount(Long companyId,Integer num,String serviceName){
        return countService.checkCompanyProductCount(companyId, serviceName, num);
    }

    @Override
    public int getProductCount(Long companyId,String serviceName){
        return countService.companyProductCount(companyId,serviceName);
    }

    @Override
    public void deduction(Long companyId, String serviceName, Integer num,String username,String des) {
        countService.reduceCompanyProductCount(companyId,serviceName,num,username,des);
    }
}
