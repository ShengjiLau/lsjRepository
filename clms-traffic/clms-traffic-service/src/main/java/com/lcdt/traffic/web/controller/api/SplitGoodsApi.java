package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dao.SnatchGoodsMapper;
import com.lcdt.traffic.dao.TransportWayItemsMapper;
import com.lcdt.traffic.dto.BindingSplitDto;
import com.lcdt.traffic.dto.BindingSplitParamsDto;
import com.lcdt.traffic.dto.SplitGoodsParamsDto;
import com.lcdt.traffic.dto.WaybillParamsDto;
import com.lcdt.traffic.model.SnatchGoods;
import com.lcdt.traffic.model.TransportWayItems;
import com.lcdt.traffic.model.Waybill;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.service.IPlanRpcService4Wechat;
import com.lcdt.traffic.service.PlanService;
import com.lcdt.traffic.service.SplitGoodsService;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @Autowired
    private IPlanRpcService4Wechat iPlanRpcService4Wechat;



    @ApiOperation(value = "派单-直派")
    @RequestMapping(value = "/splitGoods4Direct",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_split_goods')")
    public String splitGoods4Direct(@ApiParam(value = "派单详细信息", required = true) @RequestBody SplitGoodsParamsDto dto) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        WaybillPlan waybillPlan = iPlanRpcService4Wechat.splitGoods4Direct(dto,userCompRel);
        JSONObject jsonObject = new JSONObject();
        String message = null;
        int code = -1;
        if (waybillPlan!=null) {
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_split_goods')")
    public BindingSplitDto splitGoodsLoad4Binding(@ApiParam(value = "计划ID",required = true) @RequestParam Long waybillPlanId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        WaybillParamsDto dto = new WaybillParamsDto();
        dto.setCompanyId(companyId);
        dto.setWaybillPlanId(waybillPlanId);
        WaybillPlan waybillPlan = iPlanRpcService4Wechat.loadWaybillPlan(dto);
        BindingSplitDto rdto = new BindingSplitDto();
        rdto.setWaybillPlan(waybillPlan);//计划划
        Map map = new HashMap<String,Long>();
        map.put("waybillPlanId",waybillPlanId);
        map.put("isDeleted",0);
        List<SnatchGoods> list = snatchGoodsMapper.selectByWaybillPlanId(map);
        List<SnatchGoods> list1 = new ArrayList<SnatchGoods>();
        if(list!=null && list.size()>0) { //剔除驳回记录
            for(SnatchGoods obj :list) {
                if(obj.getIsUsing()!=null && obj.getIsUsing().equals(2)) {
                    list1.add(obj);
                }
                list.removeAll(list1);
            }
        }


        rdto.setSnatchGoodsList(list);//抢单
        Map map1 = new HashMap<String,Long>();
        map1.put("waybillPlanId",waybillPlanId);
        List<TransportWayItems> transportWayItemsList = transportWayItemsMapper.selectByWaybillPlanId(map1);
        rdto.setTransportWayItemsList(transportWayItemsList); //输入项目
        return  rdto;
    }


    @ApiOperation(value = "派单-竞价")
    @RequestMapping(value = "/splitGoods4Bidding",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_split_goods')")
    public String splitGoods4Bidding(@RequestBody BindingSplitParamsDto dto) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        WaybillPlan waybillPlan = iPlanRpcService4Wechat.splitGoods4Bidding(dto,userCompRel);
        JSONObject jsonObject = new JSONObject();
        String message = null;
        int code = -1;
        if (waybillPlan!=null) {
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_split_goods')")
    public String splitGoodsCancel(@ApiParam(value = "派单主ID",required = true) @RequestParam Long splitGoodsId,
                                   @ApiParam(value = "派单明细ID",required = true) @RequestParam Long splitGoodsDetailId

                                    ) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        int flag = splitGoodsService.splitGoodsCancel(splitGoodsId, splitGoodsDetailId,loginUser, companyId);
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
