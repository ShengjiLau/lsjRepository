package com.lcdt.warehouse.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import com.lcdt.warehouse.dto.OutWhOrderDto;
import com.lcdt.warehouse.dto.OutWhOrderSearchDto;
import com.lcdt.warehouse.dto.PageBaseDto;
import com.lcdt.warehouse.service.OutWarehouseOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Date;

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

    @Autowired
    OutWarehouseOrderService outWarehouseOrderService;

    @ApiOperation("出库单新增")
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public JSONObject addOutWarehouseOrder(@RequestBody OutWhOrderDto params){
        Long companyId = SecurityInfoGetter.getCompanyId();
        User user = SecurityInfoGetter.getUser();
        params.setCompanyId(companyId);
        params.setCreateId(user.getUserId());
        params.setCreateName(user.getRealName());

        int result=outWarehouseOrderService.addOutWarehouseOrder(params);
        JSONObject jsonObject=new JSONObject();
        if(result>0){
            jsonObject.put("code",0);
            jsonObject.put("message","新增成功");
        }else{
            jsonObject.put("code",0);
            jsonObject.put("message","新增失败");
        }

        return jsonObject;
    }


    @ApiOperation("出库单列表")
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public PageBaseDto outWarehouseOrderList(OutWhOrderSearchDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());
        Page<OutWhOrderDto> inWarehouseOrderPage = outWarehouseOrderService.queryOutWarehouseOrderList(params);
        PageBaseDto pageBaseDto = new PageBaseDto(inWarehouseOrderPage.getRecords(), inWarehouseOrderPage.getTotal());
        return pageBaseDto;
    }


    @ApiOperation("入库单详细")
    @RequestMapping(value = "/order/{inorderId}", method = RequestMethod.GET)
    public OutWhOrderDto inWarehouseOrderDetail(@PathVariable Long outorderId) {
        OutWhOrderDto outWhOrderDto = new OutWhOrderDto();
        outWhOrderDto = outWarehouseOrderService.queryOutWarehouseOrder(SecurityInfoGetter.getCompanyId(), outorderId);
        return outWhOrderDto;
    }
}

