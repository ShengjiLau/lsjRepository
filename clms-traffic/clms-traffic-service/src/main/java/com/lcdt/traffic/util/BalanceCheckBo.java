package com.lcdt.traffic.util;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.pay.rpc.SmsCountService;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @AUTHOR liuh
 * @DATE 2018-01-16
 */
@Service
public class BalanceCheckBo {

    @Reference
    public SmsCountService smsCountService;

    public boolean check(Long companyId) {
        return smsCountService.checkProductCount(companyId, 1, "gms_location");
    }

    public int getGmsCount(Long companyId) {
        return smsCountService.getProductCount(companyId, "gms_location");
    }

    /**
     * userName需要从调用者处传过来，运单定时任务时，没有当前登录人
     * @param companyId
     * @param mobile
     * @param driverName
     * @param userName
     * @param serialNo
     */
    public void deductionGms(Long companyId, String mobile, String driverName,String userName, String serialNo) {
        //扣费触发人
//        String username = SecurityInfoGetter.getUser().getRealName();
        String name = null!=driverName?driverName:"";
        String des = "";
        if (null != serialNo || "".equals(serialNo)) {
            des = "定位" + name + mobile + "-" + serialNo;
        } else {
            des = "定位" + name + mobile;
        }
        smsCountService.deduction(companyId, "gms_location", 1, userName, des);
    }
}
