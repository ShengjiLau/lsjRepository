package com.lcdt.driver.wechat.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.traffic.service.WaybillRpcService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by lyqishan on 2018/3/19
 */
@RestController
@RequestMapping("/waybill")
public class WaybillApi {
    @Reference
    WaybillRpcService waybillRpcService;
}
