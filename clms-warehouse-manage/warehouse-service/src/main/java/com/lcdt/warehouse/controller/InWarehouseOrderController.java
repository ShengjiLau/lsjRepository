package com.lcdt.warehouse.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import com.lcdt.warehouse.dto.InWarehouseOrderDto;
import com.lcdt.warehouse.dto.InWarehouseOrderSearchParamsDto;
import com.lcdt.warehouse.dto.PageBaseDto;
import com.lcdt.warehouse.entity.InWarehouseOrder;
import com.lcdt.warehouse.service.InWarehouseOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 *  前端控制器
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
    @RequestMapping(value = "/order",method = RequestMethod.POST)
    public JSONObject InWarehouseOrder(InWarehouseOrderDto params){
        Long companyId = SecurityInfoGetter.getCompanyId();
        User user=SecurityInfoGetter.getUser();
        params.setCompanyId(companyId);
        params.setCreateId(user.getUserId());
        params.setCreateName(user.getRealName());
        params.setCreateDate(new Date());
        int result=inWarehouseOrderService.addInWarehouseOrder(params);
        JSONObject jsonObject=new JSONObject();
        if(result>0){
            jsonObject.put("code",0);
            jsonObject.put("message","添加成功");
        }else {
            jsonObject.put("code",-1);
            jsonObject.put("message","添加失败");
        }
        return jsonObject;
    }

    @ApiOperation("入库单列表")
    @RequestMapping(value = "/order",method = RequestMethod.GET)
    public PageBaseDto InWarehouseOrderList(InWarehouseOrderSearchParamsDto params){
        Long companyId = SecurityInfoGetter.getCompanyId();
        User user=SecurityInfoGetter.getUser();
        params.setCompanyId(companyId);
        Page<InWarehouseOrder> inWarehouseOrderPage=inWarehouseOrderService.queryInWarehouseOrderList(params);
        PageBaseDto pageBaseDto = new PageBaseDto(inWarehouseOrderPage.getRecords(), inWarehouseOrderPage.getTotal());
        return pageBaseDto;
    }
}

