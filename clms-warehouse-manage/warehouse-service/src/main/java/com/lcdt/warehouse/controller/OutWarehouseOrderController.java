package com.lcdt.warehouse.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import com.lcdt.warehouse.dto.*;
import com.lcdt.warehouse.service.OutWarehouseOrderService;
import com.lcdt.warehouse.vo.ConstantVO;
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
            jsonObject.put("code",-1);
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


    @ApiOperation("出库单详细")
    @RequestMapping(value = "/order/{outorderId}", method = RequestMethod.GET)
    public OutWhOrderDto inWarehouseOrderDetail(@PathVariable Long outorderId) {
        OutWhOrderDto outWhOrderDto = new OutWhOrderDto();
        outWhOrderDto = outWarehouseOrderService.queryOutWarehouseOrder(SecurityInfoGetter.getCompanyId(), outorderId);
        return outWhOrderDto;
    }

    @ApiOperation("出库单出库")
    @RequestMapping(value = "/order/outbound", method = RequestMethod.PATCH)
    public JSONObject outbound(@RequestBody OutWhOrderOutboundParamsDto params) {
        ModifyOutOrderStatusParamsDto statusParams = new ModifyOutOrderStatusParamsDto();
        User user = SecurityInfoGetter.getUser();
        statusParams.setOutorderId(params.getOutorderId());
        statusParams.setUpdateId(user.getUserId());
        statusParams.setUpdateName(user.getRealName());
        statusParams.setOrderStatus(ConstantVO.OUT_ORDER_STATUS_HAVE_OUTBOUND);
        statusParams.setCompanyId(SecurityInfoGetter.getCompanyId());
        statusParams.setOutboundTime(params.getOutboundTime());

        boolean result = outWarehouseOrderService.outbound(statusParams, params.getOutOrderGoodsInfoList());
        JSONObject jsonObject = new JSONObject();
        if (result) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "出库成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "出库失败");
        }
        return jsonObject;
    }

    @ApiOperation("出库单取消")
    @RequestMapping(value = "/order/cancel/{outorderId}", method = RequestMethod.PATCH)
    public JSONObject cancelOutbound(@PathVariable long outorderId) {
        ModifyOutOrderStatusParamsDto params = new ModifyOutOrderStatusParamsDto();
        User user = SecurityInfoGetter.getUser();
        params.setOutorderId(outorderId);
        params.setUpdateId(user.getUserId());
        params.setUpdateName(user.getRealName());
        params.setOrderStatus(ConstantVO.OUT_ORDER_STATUS_HAVE_CANCEL);
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        boolean result = outWarehouseOrderService.modifyOutOrderStatus(params);
        JSONObject jsonObject = new JSONObject();
        if (result) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "取消出库单成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "取消出库单失败");
        }
        return jsonObject;
    }

    @ApiOperation("出库配仓信息，计划用")
    @RequestMapping(value = "/order/distribution/records", method = RequestMethod.GET)
    public PageBaseDto queryDisRecords(@RequestParam Long outPlanId) {
        PageBaseDto pageBaseDto = new PageBaseDto(outWarehouseOrderService.queryOutOrderDisRecords(SecurityInfoGetter.getCompanyId(), outPlanId));
        return pageBaseDto;
    }

}

