package com.lcdt.warehouse.controller;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code generate
 * @since 2018-05-25
 */
@RestController
@RequestMapping("/wh/out")
@Api(value = "出库单api", description = "出库单操作")
public class OutWarehouseOrderController {
    
    @ApiOperation("出库单新增")
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public JSONObject addOutWarehouseOrder(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",0);
        jsonObject.put("message","操作成功");
        return jsonObject;
    }
}

