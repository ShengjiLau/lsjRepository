package com.lcdt.traffic.web.controller.api;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.service.PlanService;
import com.lcdt.traffic.web.dto.PageBaseDto;
import com.lcdt.traffic.web.dto.WaybillPlanListParamsDto;
import com.lcdt.userinfo.model.Group;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2017/12/26.
 */
@RestController
@RequestMapping("/api/clientplan")
@Api(value = "客户计划",description = "客户计划接口")
public class ClientPlanApi {


    @Autowired
    private PlanService planService;


    @ApiOperation("客户计划-列表-竞价")
    @RequestMapping(value = "/clientPlanList4Bidding",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_client_plan_list_4_bidding')")
    public PageBaseDto clientPlanList4Bidding(@Validated WaybillPlanListParamsDto dto,
                                              @ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                                              @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);
        map.put("isDeleted",0); //未删除的
        if (dto.getSendOrderType()>0) {
            map.put("sendOrderType",dto.getSendOrderType());
        }
        if (StringUtil.isNotEmpty(dto.getCustomerName())) {
            map.put("customerName",dto.getCustomerName());
        }
        if (StringUtil.isNotEmpty(dto.getSerialCode())) {
            map.put("serialCode",dto.getSerialCode());
        }
        if (StringUtil.isNotEmpty(dto.getPlanStatus())) {
            map.put("planStatus",dto.getPlanStatus());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveProvince())) {
            map.put("receiveProvince",dto.getReceiveProvince());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCity())) {
            map.put("receiveCity",dto.getReceiveCity());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCounty())) {
            map.put("receiveCounty",dto.getReceiveCounty());
        }
        if (StringUtil.isNotEmpty(dto.getPubdateBegin())) { //发布时间
            map.put("pubdateBegin",dto.getPubdateBegin());
        }
        if (StringUtil.isNotEmpty(dto.getPubdateEnd())) {
            map.put("pubdateEnd",dto.getPubdateEnd());
        }
        if (StringUtil.isNotEmpty(dto.getGoodsInfo())) {
            map.put("goodsInfo",dto.getGoodsInfo());
        }
        StringBuffer sb = new StringBuffer();
        if (StringUtil.isNotEmpty(dto.getGroupIds())) {//业务组
            sb.append(" find_in_set('"+dto.getGroupIds()+"',group_ids)"); //客户表
        } else {
            sb.append("(");
            List<Group> groupList = SecurityInfoGetter.groups();
            for(int i=0;i<groupList.size();i++) {
                Group group = groupList.get(i);
                sb.append(" find_in_set('"+dto.getGroupIds()+"',group_ids)"); //客户表
                if(i!=groupList.size()-1){
                    sb.append(" or ");
                }
            }
            sb.append(")");
        }

        map.put("groupIds",sb.toString());//客户

        PageInfo pageInfo = planService.clientPlanList(map);


        PageBaseDto dto1 = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto1;
    }



    @ApiOperation("客户计划-列表-已报价")
    @RequestMapping(value = "/clientPlanList4Offer",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_client_plan_list_4_offer')")
    public PageBaseDto clientPlanList4Offer(@Validated WaybillPlanListParamsDto dto,
                                            @ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                                            @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {
        return null;
    }


    @ApiOperation("客户计划-列表-已错过")
    @RequestMapping(value = "/clientPlanList4Pass",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_client_plan_list_4_pass')")
    public PageBaseDto clientPlanList4Pass(@Validated WaybillPlanListParamsDto dto,
                                           @ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                                           @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {
        return null;
    }


    @ApiOperation("客户计划-列表-派车中")
    @RequestMapping(value = "/clientPlanList4VehicleDoing",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_client_plan_list_4_vehicle_doing')")
    public PageBaseDto clientPlanList4VehicleDoing(@Validated WaybillPlanListParamsDto dto,
                                                   @ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                                                   @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {
        return null;
    }

    @ApiOperation("客户计划-列表-已派车")
    @RequestMapping(value = "/clientPlanList4VehicleHave",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_client_plan_list_4_vehicle_have')")
    public PageBaseDto clientPlanList4VehicleHave(@Validated WaybillPlanListParamsDto dto,
                                                  @ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                                                  @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {
        return null;
    }


    @ApiOperation("客户计划-列表-已完成")
    @RequestMapping(value = "/clientPlanList4Completed",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_client_plan_list_4_vehicle_completed')")
    public PageBaseDto clientPlanList4Completed(@Validated WaybillPlanListParamsDto dto,
                                                @ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                                                @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {
        return null;
    }


    @ApiOperation("客户计划-列表-已取消")
    @RequestMapping(value = "/clientPlanList4Cancel",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_client_plan_list_4_vehicle_cancel')")
    public PageBaseDto clientPlanList4Cancel(@Validated WaybillPlanListParamsDto dto,
                                             @ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                                             @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {
        return null;
    }
}
