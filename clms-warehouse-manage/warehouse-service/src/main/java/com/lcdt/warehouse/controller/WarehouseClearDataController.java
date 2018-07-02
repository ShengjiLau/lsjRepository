package com.lcdt.warehouse.controller;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import com.lcdt.warehouse.dto.InWarehouseOrderDto;
import com.lcdt.warehouse.rpc.WarehouseClearDataRpcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lyqishan on 2018/7/2
 */

@RestController
@RequestMapping("/wh")
@Api(value = "清空数据api", description = "清空操作")
public class WarehouseClearDataController {

    @Autowired
    WarehouseClearDataRpcService warehouseClearDataRpcService;

//    @ApiOperation("入库单新增")
//    @PostMapping(value = "/clear/{companyId}")
//    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_clear_data')")
//    public JSONObject clearWarehouseData(@PathVariable Long companyId) {
//        warehouseClearDataRpcService.clearWarehouseData(companyId);
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("code",0);
//        jsonObject.put("message","清空完成");
//        return jsonObject;
//    }
}
