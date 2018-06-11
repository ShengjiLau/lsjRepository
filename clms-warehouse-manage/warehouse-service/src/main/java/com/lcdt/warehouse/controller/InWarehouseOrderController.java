package com.lcdt.warehouse.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import com.lcdt.warehouse.dto.*;
import com.lcdt.warehouse.entity.InWarehouseOrder;
import com.lcdt.warehouse.service.InWarehouseOrderService;
import com.lcdt.warehouse.vo.ConstantVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
@RestController
@RequestMapping("/wh/in")
@Api(value = "入库单api", description = "入库单操作")
public class InWarehouseOrderController {

    @Autowired
    InWarehouseOrderService inWarehouseOrderService;

    @ApiOperation("入库单新增")
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public JSONObject inWarehouseOrder(@RequestBody InWarehouseOrderDto params) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User user = SecurityInfoGetter.getUser();
        params.setCompanyId(companyId);
        params.setCreateId(user.getUserId());
        params.setCreateName(user.getRealName());
        params.setCreateDate(new Date());
        params.setInOrderStatus(1);
        int result = inWarehouseOrderService.addInWarehouseOrder(params);
        JSONObject jsonObject = new JSONObject();
        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "添加失败");
        }
        return jsonObject;
    }

    @ApiOperation("入库单列表")
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public PageBaseDto inWarehouseOrderList(InWarehouseOrderSearchParamsDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());
        Page<InWarehouseOrderDto> inWarehouseOrderPage = inWarehouseOrderService.queryInWarehouseOrderList(params);
        PageBaseDto pageBaseDto = new PageBaseDto(inWarehouseOrderPage.getRecords(), inWarehouseOrderPage.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("入库单详细")
    @RequestMapping(value = "/order/{inorderId}", method = RequestMethod.GET)
    public InWarehouseOrderDto inWarehouseOrderDetail(@PathVariable long inorderId) {
        InWarehouseOrderDto inWarehouseOrderDto=new InWarehouseOrderDto();
        inWarehouseOrderDto=inWarehouseOrderService.queryInWarehouseOrder(SecurityInfoGetter.getCompanyId(),inorderId);
        return inWarehouseOrderDto;
    }

    @ApiOperation("入库单入库")
    @RequestMapping(value = "/order/storage", method = RequestMethod.PATCH)
    public JSONObject inStorage(@RequestBody InWarehouseOrderStorageParamsDto params){
        ModifyInOrderStatusParamsDto statusParams=new ModifyInOrderStatusParamsDto();
        User user=SecurityInfoGetter.getUser();
        statusParams.setInorderId(params.getInorderId());
        statusParams.setUpdateId(user.getUserId());
        statusParams.setUpdateName(user.getRealName());
        statusParams.setInOrderStatus(ConstantVO.IN_ORDER_STATUS_HAVE_STORAGE);
        statusParams.setCompanyId(SecurityInfoGetter.getCompanyId());

        boolean result=inWarehouseOrderService.storage(statusParams,params.getGoodsInfoDtoList());
        JSONObject jsonObject = new JSONObject();
        if (result) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "入库成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "入库失败");
        }
        return jsonObject;
    }
    @ApiOperation("入库单取消")
    @RequestMapping(value = "/order/cancel/{inorderId}", method = RequestMethod.PATCH)
    public JSONObject cancelStorage(@PathVariable long inorderId){
        ModifyInOrderStatusParamsDto params=new ModifyInOrderStatusParamsDto();
        User user=SecurityInfoGetter.getUser();
        params.setInorderId(inorderId);
        params.setUpdateId(user.getUserId());
        params.setUpdateName(user.getRealName());
        params.setInOrderStatus(ConstantVO.IN_ORDER_STATUS_HAVE_CANCEL);
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        boolean result=inWarehouseOrderService.modifyInOrderStatus(params);
        JSONObject jsonObject = new JSONObject();
        if (result) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "取消入库单成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "取消入库单失败");
        }
        return jsonObject;
    }

    @ApiOperation("配仓信息，计划用")
    @RequestMapping(value = "/order/distribution/records", method = RequestMethod.GET)
    public PageBaseDto queryDisRecords(@RequestParam Long planId) {
        PageBaseDto pageBaseDto = new PageBaseDto(inWarehouseOrderService.queryDisRecords(SecurityInfoGetter.getCompanyId(),planId));
        return pageBaseDto;
    }


    @ApiOperation("概览入库单已完成数量")
    @RequestMapping(value = "/inWarehouseNum", method = RequestMethod.GET)
    public JSONObject inWarehouseNum(InWarehouseOrderSearchParamsDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",inWarehouseOrderService.selectInWarehouseNum(params));

        return jo;
    }
}

