package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dto.SnatchOfferDto;
import com.lcdt.traffic.dto.SplitVehicleDto;
import com.lcdt.traffic.exception.WaybillPlanException;
import com.lcdt.traffic.model.SnatchGoods;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.service.CustomerPlanService;
import com.lcdt.traffic.service.PlanService;
import com.lcdt.traffic.web.dto.PageBaseDto;
import com.lcdt.traffic.web.dto.WaybillDto;
import com.lcdt.traffic.web.dto.WaybillParamsDto;
import com.lcdt.traffic.web.dto.WaybillPlanListParamsDto;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2017/12/26.
 */
@RestController
@RequestMapping("/api/customerplan")
@Api(value = "客户计划",description = "客户计划接口")
public class CustomerPlanApi {

    @Autowired
    private CustomerPlanService customerPlanService;

    @Autowired
    private PlanService planService;


    @ApiOperation("客户计划-列表-竞价")
    @RequestMapping(value = "/customerPlanList4Bidding",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_plan_list_4_bidding')")
    public PageBaseDto customerPlanList4Bidding(@Validated WaybillPlanListParamsDto dto,
                                              @ApiParam(value = "页码",required = true,defaultValue = "1") @RequestParam Integer pageNo,
                                              @ApiParam(value = "每页显示条数",required = true,defaultValue = "10") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);
        map.put("isDeleted",0); //未删除的

        if (StringUtil.isNotEmpty(dto.getSerialCode())) { //流水号
            map.put("serialCode",dto.getSerialCode());
        }

        if (StringUtil.isNotEmpty(dto.getCustomerName())) { //客户名称
            map.put("customerName",dto.getCustomerName());
        }
        //收货地
        if (StringUtil.isNotEmpty(dto.getReceiveProvince())) {
            map.put("receiveProvince",dto.getReceiveProvince());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCity())) {
            map.put("receiveCity",dto.getReceiveCity());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCounty())) {
            map.put("receiveCounty",dto.getReceiveCounty());
        }
        //计划发布时间
        if (StringUtil.isNotEmpty(dto.getPubdateBegin())) { //计划发布时间
            map.put("pubdateBegin",dto.getPubdateBegin());
        }
        if (StringUtil.isNotEmpty(dto.getPubdateEnd())) {
            map.put("pubdateEnd",dto.getPubdateEnd());
        }
        //货物信息
        if (StringUtil.isNotEmpty(dto.getGoodsInfo())) {
            map.put("goodsInfo",dto.getGoodsInfo());
        }

        //组权限信息
        StringBuffer sb = new StringBuffer();
        if (StringUtil.isNotEmpty(dto.getGroupIds())) {//业务组
            sb.append(" find_in_set('"+dto.getGroupIds()+"',group_ids)"); //客户表
            map.put("groupIds",sb.toString());//客户
        } else {
            sb.append("(");
            List<Group> groupList = SecurityInfoGetter.groups();
            for(int i=0;i<groupList.size();i++) {
                Group group = groupList.get(i);
                sb.append(" find_in_set('"+group.getGroupId()+"',group_ids)"); //客户表
                if(i!=groupList.size()-1){
                    sb.append(" or ");
                }
            }
            sb.append(")");
            if(groupList.size()>0) {
                map.put("groupIds", sb.toString());//客户
            }
        }

        PageInfo pageInfo = customerPlanService.customerPlanList4Bidding(map);
        PageBaseDto dto1 = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto1;
    }



    @ApiOperation("客户计划-列表-已报价")
    @RequestMapping(value = "/customerPlanList4Offer",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_plan_list_4_offer')")
    public PageBaseDto customerPlanList4Offer(@Validated WaybillPlanListParamsDto dto,
                                            @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                            @ApiParam(value = "每页显示条数",required = true, defaultValue = "10") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);

        if (StringUtil.isNotEmpty(dto.getSerialCode())) { //流水号
            map.put("serialCode",dto.getSerialCode());
        }

        if (StringUtil.isNotEmpty(dto.getCustomerName())) { //客户名称
            map.put("customerName",dto.getCustomerName());
        }

        if (StringUtil.isNotEmpty(dto.getReceiveProvince())) { //收货地
            map.put("receiveProvince",dto.getReceiveProvince());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCity())) {
            map.put("receiveCity",dto.getReceiveCity());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCounty())) {
            map.put("receiveCounty",dto.getReceiveCounty());
        }

        if (StringUtil.isNotEmpty(dto.getPubdateBegin())) { //计划发布时间
            map.put("pubdateBegin",dto.getPubdateBegin());
        }

        if (StringUtil.isNotEmpty(dto.getPubdateEnd())) {
            map.put("pubdateEnd",dto.getPubdateEnd());
        }

        if (StringUtil.isNotEmpty(dto.getGoodsInfo())) {//货物信息
            map.put("goodsInfo",dto.getGoodsInfo());
        }

        StringBuffer sb = new StringBuffer(); //组权限信息
        if (StringUtil.isNotEmpty(dto.getGroupIds())) {//业务组
            sb.append(" find_in_set('"+dto.getGroupIds()+"',group_ids)"); //客户表
            map.put("groupIds",sb.toString());//客户
        } else {
            sb.append("(");
            List<Group> groupList = SecurityInfoGetter.groups();
            for(int i=0;i<groupList.size();i++) {
                Group group = groupList.get(i);
                sb.append(" find_in_set('"+group.getGroupId()+"',group_ids)"); //客户表
                if(i!=groupList.size()-1){
                    sb.append(" or ");
                }
            }
            sb.append(")");
            if(groupList.size()>0) {
                map.put("groupIds", sb.toString());//客户
            }
        }

        PageInfo pageInfo = customerPlanService.customerPlanList4Offer(map);
        PageBaseDto dto1 = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto1;
    }


    @ApiOperation("客户计划-列表-已错过")
    @RequestMapping(value = "/customerPlanList4Pass",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_plan_list_4_pass')")
    public PageBaseDto customerPlanList4Pass(@Validated WaybillPlanListParamsDto dto,
                                           @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                           @ApiParam(value = "每页显示条数",required = true, defaultValue = "10") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);

        if (StringUtil.isNotEmpty(dto.getSerialCode())) { //流水号
            map.put("serialCode",dto.getSerialCode());
        }

        if (StringUtil.isNotEmpty(dto.getCustomerName())) { //客户名称
            map.put("customerName",dto.getCustomerName());
        }
        //收货地
        if (StringUtil.isNotEmpty(dto.getReceiveProvince())) {
            map.put("receiveProvince",dto.getReceiveProvince());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCity())) {
            map.put("receiveCity",dto.getReceiveCity());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCounty())) {
            map.put("receiveCounty",dto.getReceiveCounty());
        }
        //计划发布时间
        if (StringUtil.isNotEmpty(dto.getPubdateBegin())) { //计划发布时间
            map.put("pubdateBegin",dto.getPubdateBegin());
        }
        if (StringUtil.isNotEmpty(dto.getPubdateEnd())) {
            map.put("pubdateEnd",dto.getPubdateEnd());
        }
        //货物信息
        if (StringUtil.isNotEmpty(dto.getGoodsInfo())) {
            map.put("goodsInfo",dto.getGoodsInfo());
        }

        //组权限信息
        StringBuffer sb = new StringBuffer();
        if (StringUtil.isNotEmpty(dto.getGroupIds())) {//业务组
            sb.append(" find_in_set('"+dto.getGroupIds()+"',group_ids)"); //客户表
            map.put("groupIds",sb.toString());//客户
        } else {
            sb.append("(");
            List<Group> groupList = SecurityInfoGetter.groups();
            for(int i=0;i<groupList.size();i++) {
                Group group = groupList.get(i);
                sb.append(" find_in_set('"+group.getGroupId()+"',group_ids)"); //客户表
                if(i!=groupList.size()-1){
                    sb.append(" or ");
                }
            }
            sb.append(")");
            if(groupList.size()>0) {
                map.put("groupIds", sb.toString());//客户
            }
        }

        PageInfo pageInfo = customerPlanService.customerPlanList4Pass(map);
        PageBaseDto dto1 = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto1;
    }


    @ApiOperation("客户计划-列表-派车中")
    @RequestMapping(value = "/customerPlanList4VehicleDoing",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_plan_list_4_vehicle_doing')")
    public PageBaseDto customerPlanList4VehicleDoing(@Validated WaybillPlanListParamsDto dto,
                                                   @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                                   @ApiParam(value = "每页显示条数",required = true, defaultValue = "2") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);

        if (StringUtil.isNotEmpty(dto.getSerialCode())) { //流水号
            map.put("serialCode",dto.getSerialCode());
        }

        if (StringUtil.isNotEmpty(dto.getCustomerName())) { //客户名称
            map.put("customerName",dto.getCustomerName());
        }
        //收货地
        if (StringUtil.isNotEmpty(dto.getReceiveProvince())) {
            map.put("receiveProvince",dto.getReceiveProvince());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCity())) {
            map.put("receiveCity",dto.getReceiveCity());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCounty())) {
            map.put("receiveCounty",dto.getReceiveCounty());
        }

        if (StringUtil.isNotEmpty(dto.getDisDateBegin())) { //派单时间-开始
            map.put("disDateBegin",dto.getDisDateBegin());
        }
        if (StringUtil.isNotEmpty(dto.getPubdateEnd())) { //派单时间-结束
            map.put("disDateEnd",dto.getDisDateEnd());
        }
        //货物信息
        if (StringUtil.isNotEmpty(dto.getGoodsInfo())) {
            map.put("goodsInfo",dto.getGoodsInfo());
        }

        //组权限信息
        StringBuffer sb = new StringBuffer();
        if (StringUtil.isNotEmpty(dto.getGroupIds())) {//业务组
            sb.append(" find_in_set('"+dto.getGroupIds()+"',group_ids)"); //客户表
            map.put("groupIds",sb.toString());//客户
        } else {
            sb.append("(");
            List<Group> groupList = SecurityInfoGetter.groups();
            for(int i=0;i<groupList.size();i++) {
                Group group = groupList.get(i);
                sb.append(" find_in_set('"+group.getGroupId()+"',group_ids)"); //客户表
                if(i!=groupList.size()-1){
                    sb.append(" or ");
                }
            }
            sb.append(")");
            if(groupList.size()>0) {
                map.put("groupIds", sb.toString());//客户
            }
        }

        PageInfo pageInfo = customerPlanService.customerPlanList4VehicleDoing(map);
        PageBaseDto dto1 = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto1;
    }

    @ApiOperation("客户计划-列表-已派车")
    @RequestMapping(value = "/customerPlanList4VehicleHave",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_plan_list_4_vehicle_have')")
    public PageBaseDto customerPlanList4VehicleHave(@Validated WaybillPlanListParamsDto dto,
                                                  @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                                  @ApiParam(value = "每页显示条数",required = true, defaultValue = "10") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);

        if (StringUtil.isNotEmpty(dto.getSerialCode())) { //流水号
            map.put("serialCode",dto.getSerialCode());
        }

        if (StringUtil.isNotEmpty(dto.getCustomerName())) { //客户名称
            map.put("customerName",dto.getCustomerName());
        }
        //收货地
        if (StringUtil.isNotEmpty(dto.getReceiveProvince())) {
            map.put("receiveProvince",dto.getReceiveProvince());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCity())) {
            map.put("receiveCity",dto.getReceiveCity());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCounty())) {
            map.put("receiveCounty",dto.getReceiveCounty());
        }

        if (StringUtil.isNotEmpty(dto.getDisDateBegin())) { //派单时间-开始
            map.put("disDateBegin",dto.getDisDateBegin());
        }
        if (StringUtil.isNotEmpty(dto.getPubdateEnd())) { //派单时间-结束
            map.put("disDateEnd",dto.getDisDateEnd());
        }
        //货物信息
        if (StringUtil.isNotEmpty(dto.getGoodsInfo())) {
            map.put("goodsInfo",dto.getGoodsInfo());
        }

        //组权限信息
        StringBuffer sb = new StringBuffer();
        if (StringUtil.isNotEmpty(dto.getGroupIds())) {//业务组
            sb.append(" find_in_set('"+dto.getGroupIds()+"',group_ids)"); //客户表
            map.put("groupIds",sb.toString());//客户
        } else {
            sb.append("(");
            List<Group> groupList = SecurityInfoGetter.groups();
            for(int i=0;i<groupList.size();i++) {
                Group group = groupList.get(i);
                sb.append(" find_in_set('"+group.getGroupId()+"',group_ids)"); //客户表
                if(i!=groupList.size()-1){
                    sb.append(" or ");
                }
            }
            sb.append(")");
            if(groupList.size()>0) {
                map.put("groupIds", sb.toString());//客户
            }
        }
        PageInfo pageInfo = customerPlanService.customerPlanList4VehicleHave(map);
        PageBaseDto dto1 = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto1;
    }


    @ApiOperation("客户计划-列表-已完成")
    @RequestMapping(value = "/customerPlanList4Completed",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_plan_list_4_vehicle_completed')")
    public PageBaseDto customerPlanList4Completed(@Validated WaybillPlanListParamsDto dto,
                                                @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                                @ApiParam(value = "每页显示条数",required = true, defaultValue = "10") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);

        if (StringUtil.isNotEmpty(dto.getSerialCode())) { //流水号
            map.put("serialCode",dto.getSerialCode());
        }

        if (StringUtil.isNotEmpty(dto.getCustomerName())) { //客户名称
            map.put("customerName",dto.getCustomerName());
        }
        //收货地
        if (StringUtil.isNotEmpty(dto.getReceiveProvince())) {
            map.put("receiveProvince",dto.getReceiveProvince());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCity())) {
            map.put("receiveCity",dto.getReceiveCity());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCounty())) {
            map.put("receiveCounty",dto.getReceiveCounty());
        }

        if (StringUtil.isNotEmpty(dto.getDisDateBegin())) { //派单时间-开始
            map.put("disDateBegin",dto.getDisDateBegin());
        }
        if (StringUtil.isNotEmpty(dto.getPubdateEnd())) { //派单时间-结束
            map.put("disDateEnd",dto.getDisDateEnd());
        }
        //货物信息
        if (StringUtil.isNotEmpty(dto.getGoodsInfo())) {
            map.put("goodsInfo",dto.getGoodsInfo());
        }

        //组权限信息
        StringBuffer sb = new StringBuffer();
        if (StringUtil.isNotEmpty(dto.getGroupIds())) {//业务组
            sb.append(" find_in_set('"+dto.getGroupIds()+"',group_ids)"); //客户表
            map.put("groupIds",sb.toString());//客户
        } else {
            sb.append("(");
            List<Group> groupList = SecurityInfoGetter.groups();
            for(int i=0;i<groupList.size();i++) {
                Group group = groupList.get(i);
                sb.append(" find_in_set('"+group.getGroupId()+"',group_ids)"); //客户表
                if(i!=groupList.size()-1){
                    sb.append(" or ");
                }
            }
            sb.append(")");
            if(groupList.size()>0) {
                map.put("groupIds", sb.toString());//客户
            }
        }

        PageInfo pageInfo = customerPlanService.customerPlanList4Completed(map);
        PageBaseDto dto1 = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto1;
    }


    @ApiOperation("客户计划-列表-已取消")
    @RequestMapping(value = "/customerPlanList4Cancel",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_plan_list_4_vehicle_cancel')")
    public PageBaseDto customerPlanList4Cancel(@Validated WaybillPlanListParamsDto dto,
                                             @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                             @ApiParam(value = "每页显示条数",required = true, defaultValue = "10") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);

        if (StringUtil.isNotEmpty(dto.getSerialCode())) { //流水号
            map.put("serialCode",dto.getSerialCode());
        }

        if (StringUtil.isNotEmpty(dto.getCustomerName())) { //客户名称
            map.put("customerName",dto.getCustomerName());
        }
        //收货地
        if (StringUtil.isNotEmpty(dto.getReceiveProvince())) {
            map.put("receiveProvince",dto.getReceiveProvince());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCity())) {
            map.put("receiveCity",dto.getReceiveCity());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCounty())) {
            map.put("receiveCounty",dto.getReceiveCounty());
        }

        if (StringUtil.isNotEmpty(dto.getDisDateBegin())) { //派单时间-开始
            map.put("disDateBegin",dto.getDisDateBegin());
        }
        if (StringUtil.isNotEmpty(dto.getPubdateEnd())) { //派单时间-结束
            map.put("disDateEnd",dto.getDisDateEnd());
        }
        //货物信息
        if (StringUtil.isNotEmpty(dto.getGoodsInfo())) {
            map.put("goodsInfo",dto.getGoodsInfo());
        }

        //组权限信息
        StringBuffer sb = new StringBuffer();
        if (StringUtil.isNotEmpty(dto.getGroupIds())) {//业务组
            sb.append(" find_in_set('"+dto.getGroupIds()+"',group_ids)"); //客户表
            map.put("groupIds",sb.toString());//客户
        } else {
            sb.append("(");
            List<Group> groupList = SecurityInfoGetter.groups();
            for(int i=0;i<groupList.size();i++) {
                Group group = groupList.get(i);
                sb.append(" find_in_set('"+group.getGroupId()+"',group_ids)"); //客户表
                if(i!=groupList.size()-1){
                    sb.append(" or ");
                }
            }
            sb.append(")");
            if(groupList.size()>0) {
                map.put("groupIds", sb.toString());//客户
            }
        }

        PageInfo pageInfo = customerPlanService.customerPlanList4Cancel(map);
        PageBaseDto dto1 = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto1;
    }


    @ApiOperation("客户计划-报价-信息拉取")
    @RequestMapping(value = "/customerPlanOfferLoadData",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_plan_offer_load_data')")
    public WaybillPlan customerPlanOfferOwner(@ApiParam(value = "计划ID",required = true) @RequestParam Long waybillPlanId,
                                            @ApiParam(value = "创建计划企业ID",required = true) @RequestParam Long companyId) {

        WaybillParamsDto dto = new WaybillParamsDto();
        dto.setCompanyId(companyId);
        dto.setWaybillPlanId(waybillPlanId);
        try {
            WaybillPlan waybillPlan = planService.loadWaybillPlan(dto);
            return  waybillPlan;
        } catch (WaybillPlanException e) {
            throw new WaybillPlanException(e.getMessage());
        }
    }



    @ApiOperation("客户计划-报价")
    @RequestMapping(value = "/customerPlanOfferOwn",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_plan_offer_own')")
    public String customerPlanOfferOwn(@RequestBody SnatchOfferDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User user = SecurityInfoGetter.getUser();
        SnatchGoods snatchGoods = new SnatchGoods();
        snatchGoods.setOfferId(user.getUserId());
        snatchGoods.setOfferName(user.getRealName());
        snatchGoods.setCreateId(user.getUserId());
        snatchGoods.setCreateName(user.getRealName());
        snatchGoods.setUpdateId(user.getUserId());
        snatchGoods.setUpdateName(user.getRealName());
        snatchGoods.setOfferPhone(user.getPhone()); //抢单人电话
        snatchGoods.setCompanyId(companyId);
        snatchGoods.setPlanCompanyId(dto.getCompanyId());//计划企业ID
        int flag = customerPlanService.customerPlanOfferOwn(dto,snatchGoods);
        JSONObject jsonObject = new JSONObject();
        String message = null;
        int code = -1;
        if (flag>0) {
            code = 0;
        } else {
            message = "操作失败，请重试！";
        }
        jsonObject.put("message",message);
        jsonObject.put("code",code);
        return jsonObject.toString();
    }



    @ApiOperation("客户计划-派车")
    @RequestMapping(value = "/customerPlanSplitVehicle",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_plan_split_vehicle')")
    public String customerPlanSplitVehicle(@RequestBody SplitVehicleDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User user = SecurityInfoGetter.getUser();
        WaybillDto waybillDto = new WaybillDto();
        waybillDto.setCreateId(user.getUserId());
        waybillDto.setCreateName(user.getRealName());
        waybillDto.setCarrierCompanyId(companyId);
        waybillDto.setDriverId(dto.getDriverId());//司机ID
        waybillDto.setDriverName(dto.getDriverName());
        waybillDto.setDriverPhone(dto.getDriverPhone());
        waybillDto.setVechicleId(dto.getVehicleId());
        waybillDto.setVechicleNum(dto.getVechicleNum());//车牌
        waybillDto.setWaybillRemark(dto.getWaybillRemark());

        int flag = customerPlanService.customerPlanSplitVehicle(dto, waybillDto);
        JSONObject jsonObject = new JSONObject();
        String message = null;
        int code = -1;
        if (flag>0) {
            code = 0;
        } else {
            message = "操作失败，请重试！";
        }
        jsonObject.put("message",message);
        jsonObject.put("code",code);
        return jsonObject.toString();
    }






}
