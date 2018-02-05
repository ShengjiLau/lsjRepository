package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dao.SnatchGoodsMapper;
import com.lcdt.traffic.dao.TransportWayItemsMapper;
import com.lcdt.traffic.dto.BindingSplitDto;
import com.lcdt.traffic.dto.BindingSplitParamsDto;
import com.lcdt.traffic.exception.WaybillPlanException;
import com.lcdt.traffic.model.SnatchGoods;
import com.lcdt.traffic.model.SplitGoods;
import com.lcdt.traffic.model.TransportWayItems;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.service.PlanService;
import com.lcdt.traffic.service.SplitGoodsService;
import com.lcdt.traffic.web.dto.SplitGoodsParamsDto;
import com.lcdt.traffic.web.dto.WaybillParamsDto;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2017/12/21.
 */

@RestController
@RequestMapping("/api/splitoods")
@Api(value = "派单",description = "派单接口")
public class SplitGoodsApi {

    @Autowired
    private SplitGoodsService splitGoodsService;

    @Autowired
    private PlanService planService;

    @Autowired
    private SnatchGoodsMapper snatchGoodsMapper; //抢单

    @Autowired
    private TransportWayItemsMapper transportWayItemsMapper; //运输项目




    @ApiOperation("派单-直派-信息拉取")
    @RequestMapping(value = "/splitGoodsLoad4Direct",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_split_goods_load_4_direct')")
    public String splitGoodsLoad4Direct(@ApiParam(value = "计划ID",required = true) @RequestParam Long waybillPlanId) {

        return  null;
    }



    @ApiOperation(value = "派单-直派")
    @RequestMapping(value = "/splitGoods4Direct",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_split_goods_4_direct')")
    public String splitGoods4Direct(@ApiParam(value = "派单详细信息", required = true) @RequestBody SplitGoodsParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        int flag = splitGoodsService.splitGoods4Direct(dto,loginUser,companyId);
        JSONObject jsonObject = new JSONObject();
        String message = null;
        int code = -1;
        if (flag==1) {
            code = 0;
        } else {
            message = "操作失败，请重试！";
        }
        jsonObject.put("message",message);
        jsonObject.put("code",code);
        return jsonObject.toString();
    }



    @ApiOperation("派单-竞价-信息拉取")
    @RequestMapping(value = "/splitGoodsLoad4Binding",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_split_goods_load_4_binding')")
    public BindingSplitDto splitGoodsLoad4Binding(@ApiParam(value = "计划ID",required = true) @RequestParam Long waybillPlanId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        WaybillParamsDto dto = new WaybillParamsDto();
        dto.setCompanyId(companyId);
        dto.setWaybillPlanId(waybillPlanId);
        WaybillPlan waybillPlan = planService.loadWaybillPlan(dto);
        BindingSplitDto rdto = new BindingSplitDto();
        rdto.setWaybillPlan(waybillPlan);//计划划
        Map map = new HashMap<String,Long>();
        map.put("waybillPlanId",waybillPlanId);
        map.put("isDeleted",0);
        List<SnatchGoods> list = snatchGoodsMapper.selectByWaybillPlanId(map);
        rdto.setSnatchGoodsList(list);//抢单
        Map map1 = new HashMap<String,Long>();
        map1.put("waybillPlanId",waybillPlanId);
        List<TransportWayItems> transportWayItemsList = transportWayItemsMapper.selectByWaybillPlanId(map1);
        rdto.setTransportWayItemsList(transportWayItemsList); //输入项目
        return  rdto;
    }


    @ApiOperation(value = "派单-竞价")
    @RequestMapping(value = "/splitGoods4Bidding",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_split_goods_4_bidding')")
    public String splitGoods4Bidding(@RequestBody BindingSplitParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        int flag = splitGoodsService.splitGoods4Bidding(dto, loginUser, companyId);
        JSONObject jsonObject = new JSONObject();
        String message = null;
        int code = -1;
        if (flag==1) {
            code = 0;
        } else {
            message = "操作失败，请重试！";
        }
        jsonObject.put("message",message);
        jsonObject.put("code",code);
        return jsonObject.toString();
    }



    @ApiOperation(value = "派单-取消")
    @RequestMapping(value = "/splitGoodsCancel",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_split_goods_cancel')")
    public String splitGoodsCancel(@ApiParam(value = "计划ID",required = true) @RequestParam Long splitGoodsId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        int flag = splitGoodsService.splitGoodsCancel(splitGoodsId, loginUser, companyId);
        JSONObject jsonObject = new JSONObject();
        String message = null;
        int code = -1;
        if (flag==1) {
            code = 0;
        } else {
            message = "操作失败，请重试！";
        }
        jsonObject.put("message",message);
        jsonObject.put("code",code);
        return jsonObject.toString();
    }



}
