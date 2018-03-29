package com.lcdt.traffic.web.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.quartz.rpc.QuartzRpc;
import com.lcdt.traffic.model.PlanLeaveMsg;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.service.IPlanRpcService4Wechat;
import com.lcdt.traffic.service.Plan4CreateService;
import com.lcdt.traffic.service.Plan4EditService;
import com.lcdt.traffic.service.PlanService;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.PageBaseDto;
import com.lcdt.traffic.web.dto.PlanLeaveMsgParamsDto;
import com.lcdt.traffic.web.dto.WaybillParamsDto;
import com.lcdt.traffic.web.dto.WaybillPlanListParamsDto;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2017/12/13.
 */

@RestController
@RequestMapping("/api/ownplan")
@Api(value = "计划",description = "计划接口")
public class OwnPlanApi {

    @Autowired
    private Plan4CreateService plan4CreateService; //计划创建

    @Autowired
    private Plan4EditService plan4EditService; //计划编辑

    @Autowired
    private PlanService planService;

    @Autowired
    private IPlanRpcService4Wechat iPlanRpcService4Wechat;


    @ApiOperation("创建--发布")
    @RequestMapping(value = "/createPlan",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_create_plan') or hasAuthority('traffic_create_plan_1')")
    public JSONObject createPlan(@RequestBody WaybillParamsDto dto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setCompanyName(userCompRel.getCompany().getFullName()); //企业名称
        dto.setPlanSource(ConstantVO.PLAN_SOURCE_ENTERING); //计划来源-录入

        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        WaybillPlan waybillPlan = plan4CreateService.createWaybillPlan(dto, (short) 1);
        jsonObject.put("code", 0);
        jsonObject.put("message", "创建成功！");
        return jsonObject;
   }


    @ApiOperation("拉取计划信息-编辑")
    @RequestMapping(value = "/loadPlan",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_load_plan') or hasAuthority('traffic_split_goods')")
    public WaybillPlan loadPlan(@ApiParam(value = "计划ID",required = true) @RequestParam Long waybillPlanId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        WaybillParamsDto dto = new WaybillParamsDto();
        dto.setCompanyId(companyId);
        dto.setWaybillPlanId(waybillPlanId);
        WaybillPlan waybillPlan = planService.loadWaybillPlan(dto);
        return waybillPlan;
    }




    @ApiOperation("计划详细删除")
    @RequestMapping(value = "/planDetailDelete",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_plan_detail_delete')")
    public JSONObject planDetailDelete(@ApiParam(value = "计划详细ID",required = true) @RequestParam Long planDetailId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        JSONObject jsonObject = new JSONObject();
        Integer flag = planService.planDetailDelete(planDetailId,companyId);
        String message = "删除成功！";
        int code = -1;
        if (flag>0) {
            code = 0;
        } else {
            message = "删除失败，请重试！";
        }
        jsonObject.put("message",message);
        jsonObject.put("code",code);
        return jsonObject;
    }



    @ApiOperation("我的计划-列表")
    @RequestMapping(value = "/ownPlanList",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_plan_list')")
    public PageBaseDto ownPlanList(@Validated WaybillPlanListParamsDto dto,
                             @ApiParam(value = "页码",required = true,defaultValue = "1") @RequestParam Integer pageNo,
                             @ApiParam(value = "每页显示条数",required = true,defaultValue = "10") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);
        map.put("isDeleted",0); //未删除的
        StringBuffer sb = new StringBuffer();
        if (StringUtil.isNotEmpty(dto.getGroupIds())) {//业务组
            sb.append(" find_in_set('"+dto.getGroupIds()+"',group_id)");
        } else {
            List<Group> groupList = SecurityInfoGetter.groups();
            if(groupList!=null && groupList.size()>0) {
                sb.append("(");
                for(int i=0;i<groupList.size();i++) {
                    Group group = groupList.get(i);
                    sb.append(" find_in_set('"+group.getGroupId()+"',group_id)");
                    if(i!=groupList.size()-1){
                        sb.append(" or ");
                    }
                }
                sb.append(")");
            }

        }

        if (!sb.toString().isEmpty()) {
            map.put("groupIds", sb.toString());
        }

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
            map.put("pubdateBegin",dto.getPubdateBegin()+" 00:00:00");
        }
        if (StringUtil.isNotEmpty(dto.getPubdateEnd())) {
            map.put("pubdateEnd",dto.getPubdateEnd()+" 23:59:59");
        }
        if (StringUtil.isNotEmpty(dto.getGoodsInfo())) {
            map.put("goodsInfo",dto.getGoodsInfo());
        }
        PageInfo pageInfo = iPlanRpcService4Wechat.wayBillPlanList(map);
        PageBaseDto dto1 = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto1;
    }

    @ApiOperation("留言-列表")
    @RequestMapping(value = "/planLeaveMsgList",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_plan_leave_msg_list') or hasAuthority('traffic_plan_leave_msg') or hasAuthority('traffic_plan_leave_msg_1')")
    public PageBaseDto planLeaveMsgList( @ApiParam(value = "计划ID",required = true) @RequestParam Long waybillPlanId,
                                                   @ApiParam(value = "创建计划企业ID",required = true) @RequestParam Long createCompanyId,
                                                   @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                                   @ApiParam(value = "每页显示条数",required = true, defaultValue = "10") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("createCompanyId",createCompanyId); //创建计划企业ID
        map.put("waybillPlanId", waybillPlanId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);
        map.put("isDeleted",0); //未删除的
        PageInfo pageInfo = planService.planLeaveMsgList(map);
        PageBaseDto dto = new PageBaseDto();
        if (pageInfo!=null) {
            dto.setList(pageInfo.getList());
            dto.setTotal(pageInfo.getTotal());
        } else {
            dto.setList(null);
            dto.setTotal(0);
        }
        return dto;
    }


    @ApiOperation("留言-添加")
    @RequestMapping(value = "/planLeaveMsgAdd",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_plan_leave_msg_Add') or hasAuthority('traffic_plan_leave_msg') or hasAuthority('traffic_plan_leave_msg_1')")
    public JSONObject planLeaveMsgAdd(@ApiParam(value = "计划ID",required = true) @RequestParam Long waybillPlanId,
                                        @ApiParam(value = "创建计划企业ID",required = true) @RequestParam Long companyId,
                                        @ApiParam(value = "留言内容",required = true) @RequestParam String leaveMsg) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        User loginUser = SecurityInfoGetter.getUser();
        PlanLeaveMsgParamsDto dto = new PlanLeaveMsgParamsDto();
        dto.setCompanyId(userCompRel.getCompId());
        dto.setCompanyName(userCompRel.getCompany().getFullName());
        dto.setUserId(loginUser.getUserId());
        dto.setRealName(loginUser.getRealName());
        dto.setLeaveMsg(leaveMsg);
        dto.setWaybillPlanId(waybillPlanId);
        dto.setCreateCompanyId(companyId); //创建计划企业ID
        JSONObject jsonObject = new JSONObject();
        PlanLeaveMsg planLeaveMsg =  planService.planLeaveMsgAdd(dto);
        String message = "添加成功！";
        int code = -1;
        if (planLeaveMsg!=null) {
            code = 0;
        } else {
            message = "添加失败！";
        }
        jsonObject.put("message",message);
        jsonObject.put("code",code);
        return jsonObject;
    }



    @ApiOperation("计划--取消")
    @RequestMapping(value = "/cancelOwnPlan",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_cancel_own_plan')")
    public String cancelOwnPlan(@ApiParam(value = "计划ID",required = true) @RequestParam Long waybillPlanId) {
        User loginUser = SecurityInfoGetter.getUser();
        Long companyId = SecurityInfoGetter.getCompanyId();
        Integer flag = planService.ownPlanCancel(waybillPlanId,companyId,loginUser);
        JSONObject jsonObject = new JSONObject();
        String message = "取消成功！";
        int code = -1;
        if (flag==1) {
            code = 0;
        } else {
            message = "取消失败！";
        }
        jsonObject.put("message",message);
        jsonObject.put("code",code);
        return jsonObject.toString();

    }



    @ApiOperation("竞价--结束")
    @RequestMapping(value = "/biddingFinish",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_bidding_finish') or hasAuthority('traffic_customer_plan_offer')")
    public String biddingFinish(@ApiParam(value = "计划ID",required = true) @RequestParam Long waybillPlanId) {
        User loginUser = SecurityInfoGetter.getUser();
        Long companyId = SecurityInfoGetter.getCompanyId();
        WaybillPlan waybillPlan = planService.biddingFinish(waybillPlanId,companyId,loginUser);
        JSONObject jsonObject = new JSONObject();
        String message = "操作成功！";
        int code = -1;
        if (waybillPlan!=null) {
            code = 0;
        } else {
            message = "操作失败！";
        }
        jsonObject.put("message",message);
        jsonObject.put("code",code);
        return jsonObject.toString();
    }




}


