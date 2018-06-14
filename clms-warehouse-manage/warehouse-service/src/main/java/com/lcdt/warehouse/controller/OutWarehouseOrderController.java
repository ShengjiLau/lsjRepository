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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_order_add')")
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_order_search')")
    public PageBaseDto outWarehouseOrderList(OutWhOrderSearchDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());
        Page<OutWhOrderDto> inWarehouseOrderPage = outWarehouseOrderService.queryOutWarehouseOrderList(params);
        PageBaseDto pageBaseDto = new PageBaseDto(inWarehouseOrderPage.getRecords(), inWarehouseOrderPage.getTotal());
        return pageBaseDto;
    }


    @ApiOperation("出库单详细")
    @RequestMapping(value = "/order/{outorderId}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_order_detail')")
    public OutWhOrderDto inWarehouseOrderDetail(@PathVariable Long outorderId) {
        OutWhOrderDto outWhOrderDto = new OutWhOrderDto();
        outWhOrderDto = outWarehouseOrderService.queryOutWarehouseOrder(SecurityInfoGetter.getCompanyId(), outorderId);
        return outWhOrderDto;
    }

    @ApiOperation("出库单出库")
    @RequestMapping(value = "/order/outbound", method = RequestMethod.PATCH)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_order_outbound')")
    public JSONObject outbound(@RequestBody OutWhOrderOutboundParamsDto params) {
        ModifyOutOrderStatusParamsDto statusParams = new ModifyOutOrderStatusParamsDto();
        User user = SecurityInfoGetter.getUser();
        statusParams.setOutorderId(params.getOutorderId());
        statusParams.setUpdateId(user.getUserId());
        statusParams.setUpdateName(user.getRealName());
//        statusParams.setOrderStatus(ConstantVO.OUT_ORDER_STATUS_HAVE_OUTBOUND);
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_order_cancel')")
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
    public PageBaseDto queryDisOutRecords(@RequestParam Long outPlanId) {
        PageBaseDto pageBaseDto = new PageBaseDto(outWarehouseOrderService.queryOutOrderDisRecords(SecurityInfoGetter.getCompanyId(), outPlanId));
        return pageBaseDto;
    }

    @ApiOperation("概览出库单已完成数量")
    @RequestMapping(value = "/outWarehouseBillNum", method = RequestMethod.GET)
    public JSONObject outWarehouseNum(OutWhOrderSearchDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());
        String [] inOrderStatus = {"2"};
        params.setOrderStatus(inOrderStatus);

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",outWarehouseOrderService.selectOutWarehouseNum(params));

        return jo;
    }


    @ApiOperation("概览出库单已完成商品数量")
    @RequestMapping(value = "/outWarehouseProductNum", method = RequestMethod.GET)
    public JSONObject outWarehouseProductNum(OutWhOrderSearchDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",outWarehouseOrderService.selectOutWarehouseProductNum(params));

        return jo;
    }

    @ApiOperation("出入库汇总出库已完成商品数量")
    @RequestMapping(value = "/outWarehouseProductNum4Report", method = RequestMethod.GET)
    public JSONObject outWarehouseProductNum4Report(OutWhOrderSearchDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",outWarehouseOrderService.selectOutWarehouseProductNum4Report(params));

        return jo;
    }

    @ApiOperation("出入库汇总出库已完成商品")
    @RequestMapping(value = "/outWarehouseProduct4Report", method = RequestMethod.GET)
    public JSONObject outWarehouseProduct4Report(OutWhOrderSearchDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",outWarehouseOrderService.selectOutWarehouseProduct4Report(params));

        return jo;
    }


    @ApiOperation("出入库汇总出库已完成商品按仓库分组")
    @RequestMapping(value = "/outWarehouseProduct4ReportGroupWare", method = RequestMethod.GET)
    public JSONObject selectOutWarehouseProduct4ReportGroupWare(OutWhOrderSearchDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",outWarehouseOrderService.selectOutWarehouseProduct4ReportGroupWare(params));

        return jo;
    }


    @ApiOperation("出入库汇总出库已完成商品按客户分组")
    @RequestMapping(value = "/outWarehouseProduct4ReportGroupCustomer", method = RequestMethod.GET)
    public JSONObject selectOutWarehouseProduct4ReportGroupCustomer(OutWhOrderSearchDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",outWarehouseOrderService.selectOutWarehouseProduct4ReportGroupCustomer(params));

        return jo;
    }
}

