package com.lcdt.traffic.util;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.pay.rpc.SmsCountService;
import org.springframework.stereotype.Service;

/**
 * @AUTHOR liuh
 * @DATE 2018-01-16
 */
@Service
public class BalanceCheckBo {

    @Reference
    public SmsCountService smsCountService;

    public boolean check(Long companyId){
        return smsCountService.checkProductCount(companyId,1,"gms_location");
    }

    public int getGmsCount(Long companyId){
        return smsCountService.getProductCount(companyId,"gms_location");
    }

    public void deductionGms(Long companyId){
        smsCountService.deduction(companyId,"gms_location",1);
    }
}
