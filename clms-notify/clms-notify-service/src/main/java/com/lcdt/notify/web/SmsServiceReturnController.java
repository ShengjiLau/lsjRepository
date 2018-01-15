package com.lcdt.notify.web;

import com.lcdt.notify.dao.SmsLogMapper;
import com.lcdt.notify.model.SmsLog;
import com.lcdt.pay.rpc.ProductCountService;
import com.lcdt.pay.rpc.SmsCountService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//处理短信服务商的发送结果通知
@Controller
public class SmsServiceReturnController {

    @Autowired
    SmsLogMapper smsLogMapper;

    @Reference
    SmsCountService smsCountService;

    @Autowired
    ProductCountService productCountService;

    @RequestMapping("/smsreturn")
    public String receiveSmsServiceReturn(String reference) {
        if (reference != null) {
            smsCountService.reduceSmsCount(Long.valueOf(reference),SmsCountService.smsServiceProductName,1);
            return "SUCCESS";
        }
        return "SUCCESS";
    }


}
