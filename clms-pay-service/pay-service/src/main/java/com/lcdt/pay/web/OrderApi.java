package com.lcdt.pay.web;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.pay.model.CompanyServiceCount;
import com.lcdt.pay.model.PayBalance;
import com.lcdt.pay.model.PayOrder;
import com.lcdt.pay.service.CompanyBalanceService;
import com.lcdt.pay.service.CompanyServiceCountService;
import com.lcdt.pay.service.TopupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderApi {

    @Autowired
    TopupService topupService;

    @Autowired
    CompanyBalanceService balanceService;

    @Autowired
    CompanyServiceCountService companyServiceCountService;


    /**
     * 查看所有订单
     */
    @RequestMapping(value = "/topuplist",method = RequestMethod.GET)
    public List<PayOrder> orderList(){
        Long companyId = SecurityInfoGetter.getCompanyId();
        List<PayOrder> payOrders = topupService.topUpOrderList(companyId);
        return payOrders;
    }

    /**
     * 查看公司余额
     */
    @RequestMapping(value = "/companybalance",method = RequestMethod.GET)
    public PayBalance companyBalance(){
        Long companyId = SecurityInfoGetter.getCompanyId();
        PayBalance payBalance = balanceService.companyBalance(companyId);
        if (payBalance == null) {
            payBalance = new PayBalance();
            payBalance.setBalance(0);
            payBalance.setBalanceCompanyId(companyId);
        }
        return payBalance;
    }

    @RequestMapping(value = "/companyservice",method = RequestMethod.GET)
    public  ArrayListResponseWrapper<CompanyServiceCount>  companyServiceCount(){
        Long companyId = SecurityInfoGetter.getCompanyId();
        List<CompanyServiceCount> companyServiceCounts = companyServiceCountService.companyServiceCount(companyId);
        ArrayListResponseWrapper<CompanyServiceCount> companyServiceCounts1 = new ArrayListResponseWrapper<>(companyServiceCounts);
        return companyServiceCounts1;
    }

}
