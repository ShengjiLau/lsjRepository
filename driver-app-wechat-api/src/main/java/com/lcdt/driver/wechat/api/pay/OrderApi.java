package com.lcdt.driver.wechat.api.pay;

import com.lcdt.pay.service.CompanyBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderApi {

    @Autowired
    CompanyBalanceService companyBalanceService;

}
