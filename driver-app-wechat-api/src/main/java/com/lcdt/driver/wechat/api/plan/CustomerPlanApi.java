package com.lcdt.driver.wechat.api.plan;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.traffic.dto.*;
import com.lcdt.traffic.exception.WaybillPlanException;
import com.lcdt.traffic.model.SnatchGoods;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.service.ICustomerPlanRpcService4Wechat;
import com.lcdt.traffic.service.IPlanRpcService4Wechat;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2018/4/3.
 */
@RestController
@RequestMapping("/customer/plan")
public class CustomerPlanApi {

    @com.alibaba.dubbo.config.annotation.Reference(check = false)
    private ICustomerPlanRpcService4Wechat iCustomerPlanRpcService4Wechat;

    @com.alibaba.dubbo.config.annotation.Reference(check = false)
    private IPlanRpcService4Wechat iPlanRpcService4Wechat;


    @ApiOperation("竞价")
    @RequestMapping(value = "/customerPlanList4Bidding", method = RequestMethod.GET)
    public PageBaseDto customerPlanList4Bidding(CustomerPlan4ParamsDto customerPlan4ParamsDto) {
           Map map = searchCondition4Map(customerPlan4ParamsDto);
           PageInfo pg =iCustomerPlanRpcService4Wechat.customerPlanList4Bidding(map);
           PageBaseDto pageBaseDto = new PageBaseDto(pg.getList(), pg.getTotal());
          return pageBaseDto;
    }



    @ApiOperation("已报价")
    @RequestMapping(value = "/customerPlanList4Offer",method = RequestMethod.GET)
     public PageBaseDto customerPlanList4Offer(CustomerPlan4ParamsDto customerPlan4ParamsDto) {
        Map map = searchCondition4Map(customerPlan4ParamsDto);
        PageInfo pageInfo = iCustomerPlanRpcService4Wechat.customerPlanList4Offer(map);
        PageBaseDto dto = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto;
    }




    @ApiOperation("已错过")
    @RequestMapping(value = "/customerPlanList4Pass",method = RequestMethod.GET)
    public PageBaseDto customerPlanList4Pass(CustomerPlan4ParamsDto customerPlan4ParamsDto) {
        Map map = searchCondition4Map(customerPlan4ParamsDto);
        PageInfo pageInfo = iCustomerPlanRpcService4Wechat.customerPlanList4Pass(map);
        PageBaseDto dto = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto;
    }




    @ApiOperation("派车中")
    @RequestMapping(value = "/customerPlanList4VehicleDoing",method = RequestMethod.GET)
    public PageBaseDto customerPlanList4VehicleDoing(CustomerPlan4ParamsDto customerPlan4ParamsDto) {
        Map map = searchCondition4Map(customerPlan4ParamsDto);
        PageInfo pageInfo = iCustomerPlanRpcService4Wechat.customerPlanList4VehicleDoing(map);
        PageBaseDto dto = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto;
    }



    @ApiOperation("已派车")
    @RequestMapping(value = "/customerPlanList4VehicleHave",method = RequestMethod.GET)
    public PageBaseDto customerPlanList4VehicleHave(CustomerPlan4ParamsDto customerPlan4ParamsDto) {
        Map map = searchCondition4Map(customerPlan4ParamsDto);
        PageInfo pageInfo = iCustomerPlanRpcService4Wechat.customerPlanList4VehicleHave(map);
        PageBaseDto dto = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto;
    }


    @ApiOperation("已完成")
    @RequestMapping(value = "/customerPlanList4Completed",method = RequestMethod.GET)
    public PageBaseDto customerPlanList4Completed(CustomerPlan4ParamsDto customerPlan4ParamsDto) {
        Map map = searchCondition4Map(customerPlan4ParamsDto);
        PageInfo pageInfo = iCustomerPlanRpcService4Wechat.customerPlanList4Completed(map);
        PageBaseDto dto = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto;
    }

    @ApiOperation("已取消")
    @RequestMapping(value = "/customerPlanList4Cancel",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_plan_list_4_vehicle_cancel') or hasAuthority('traffic_customer_plan_list')")
    public PageBaseDto customerPlanList4Cancel(CustomerPlan4ParamsDto customerPlan4ParamsDto) {
        Map map = searchCondition4Map(customerPlan4ParamsDto);
        PageInfo pageInfo = iCustomerPlanRpcService4Wechat.customerPlanList4Cancel(map);
        PageBaseDto dto1 = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto1;
    }


    /***
     * 组合查询条件
     * @param customerPlan4ParamsDto
     * @return
     */
    private  Map searchCondition4Map(CustomerPlan4ParamsDto customerPlan4ParamsDto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Map map = new HashMap<String,String>();
        map.put("companyId", userCompRel.getCompany().getCompId());//登录企业ID
        map.put("page_no", customerPlan4ParamsDto.getPageNo());
        map.put("page_size", customerPlan4ParamsDto.getPageSize());
        map.put("isDeleted",0); //未删除的
        map.put("orderFiled",customerPlan4ParamsDto.getOrderFiled());
        map.put("orderDesc",customerPlan4ParamsDto.getOrderDesc());
        if (StringUtil.isNotEmpty(customerPlan4ParamsDto.getCustomerName())) { //客户名称
            map.put("customerName",customerPlan4ParamsDto.getCustomerName());
        }
        StringBuffer sb = new StringBuffer();
        List<Group> groupList = TokenSecurityInfoGetter.getUserCompRel().getGroups();
        if(groupList!=null && groupList.size()>0) {
            sb.append("(");
            for(int i=0;i<groupList.size();i++) {
                Group group = groupList.get(i);
                sb.append(" find_in_set('"+group.getGroupId()+"',group_ids)");
                if(i!=groupList.size()-1){
                    sb.append(" or ");
                }
            }
            sb.append(")");
        }
        return map;
    }




    @ApiOperation("报价-信息拉取")
    @RequestMapping(value = "/customerPlanOfferLoadData",method = RequestMethod.GET)
     public WaybillPlan customerPlanOfferOwner(@ApiParam(value = "计划ID",required = true) @RequestParam Long waybillPlanId,
                                               @ApiParam(value = "创建计划企业ID",required = true) @RequestParam Long companyId) {
        WaybillParamsDto dto = new WaybillParamsDto();
        dto.setCompanyId(companyId);
        dto.setWaybillPlanId(waybillPlanId);
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        try {
            WaybillPlan waybillPlan = iPlanRpcService4Wechat.loadWaybillPlan(dto);
            List<SnatchGoods> snatchGoodsList = waybillPlan.getSnatchGoodsList();
            if (snatchGoodsList!=null && snatchGoodsList.size()>0) {
                List<SnatchGoods> otherSnatchGoods = new ArrayList<SnatchGoods>(); //存储其它数据
                for (SnatchGoods obj :snatchGoodsList) {
                    if(!obj.getCompanyId().equals(userCompRel.getCompId())) {
                        otherSnatchGoods.add(obj);
                    }
                }
                snatchGoodsList.removeAll(otherSnatchGoods);
            }
            return  waybillPlan;
        } catch (WaybillPlanException e) {
            throw new WaybillPlanException(e.getMessage());
        }
    }


    @ApiOperation("报价--提交")
    @RequestMapping(value = "/customerPlanOfferOwn",method = RequestMethod.POST)
       public String customerPlanOfferOwn(@RequestBody SnatchOfferDto dto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        User user = userCompRel.getUser();
        SnatchGoods snatchGoods = new SnatchGoods();
        snatchGoods.setOfferId(user.getUserId());
        snatchGoods.setOfferName(user.getRealName());
        snatchGoods.setCreateId(user.getUserId());
        snatchGoods.setCreateName(user.getRealName());
        snatchGoods.setUpdateId(user.getUserId());
        snatchGoods.setUpdateName(user.getRealName());
        snatchGoods.setOfferPhone(user.getPhone()); //抢单人电话
        snatchGoods.setCompanyId(userCompRel.getCompany().getCompId());
        snatchGoods.setPlanCompanyId(dto.getCompanyId());//计划企业ID
        int flag = iCustomerPlanRpcService4Wechat.customerPlanOfferOwn(dto,snatchGoods);
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
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        User user = TokenSecurityInfoGetter.getUser();
        WaybillDto waybillDto = new WaybillDto();
        waybillDto.setCreateId(user.getUserId());
        waybillDto.setCreateName(user.getRealName());
        waybillDto.setCarrierCompanyId(userCompRel.getCompany().getCompId());
        waybillDto.setDriverId(dto.getDriverId());//司机ID
        waybillDto.setDriverName(dto.getDriverName());
        waybillDto.setDriverPhone(dto.getDriverPhone());
        waybillDto.setVechicleId(dto.getVehicleId());
        waybillDto.setVechicleNum(dto.getVechicleNum());//车牌
        waybillDto.setWaybillRemark(dto.getWaybillRemark());
        dto.setCompanyId(userCompRel.getCompany().getCompId());

        WaybillPlan waybillPlan = iCustomerPlanRpcService4Wechat.customerPlanSplitVehicle(dto, waybillDto);
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
        jsonObject.put("data",waybillPlan);
        return jsonObject.toString();
    }


}
