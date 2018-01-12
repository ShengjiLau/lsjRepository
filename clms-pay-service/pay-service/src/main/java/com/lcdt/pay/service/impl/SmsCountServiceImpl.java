package com.lcdt.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.pay.dao.ProductCountLogMapper;
import com.lcdt.pay.model.CompanyServiceCount;
import com.lcdt.pay.rpc.ProductCountLog;
import com.lcdt.pay.rpc.SmsCountService;
import com.lcdt.pay.service.CompanyServiceCountService;
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
    public void reduceSmsCount(Long countLogId, Long companyId, String productName) {
        CompanyServiceCount companyServiceCount = countService.reduceCompanyProductCount(companyId, productName, 1);
        ProductCountLog productCountLog = countLogMapper.selectByPrimaryKey(countLogId);
        productCountLog.setRemainNum(companyServiceCount.getProductServiceNum());
        countLogMapper.updateByPrimaryKey(productCountLog);
    }

    @Override
    public boolean checkSmsCount(Long companyId, Integer num) {
        return countService.checkCompanyProductCount(companyId, "sms_service", num);
    }
}
