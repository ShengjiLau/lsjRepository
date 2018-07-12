package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.traffic.dao.WaybillMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yangbinq on 2018/7/12.
 */
@RestController
@RequestMapping("/traffic")
@Api(value = "清空数据api", description = "清空操作")
public class TrafficClearDataController {

    @Autowired
    private WaybillMapper waybillMapper;

    @ApiOperation("清除运输业务数据")
    @RequestMapping(value = "/clear",method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_clear_data')")
    public JSONObject clearWarehouseData(Long companyId) {
        waybillMapper.clearTrafficData(companyId);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",0);
        jsonObject.put("message","清空完成");
        return jsonObject;
    }
}
