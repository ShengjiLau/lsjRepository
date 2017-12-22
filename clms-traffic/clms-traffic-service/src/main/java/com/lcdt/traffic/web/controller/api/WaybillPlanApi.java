package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.exception.WaybillPlanException;
import com.lcdt.traffic.model.PlanLeaveMsg;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.service.WaybillPlan4CreateService;
import com.lcdt.traffic.service.WaybillPlan4EditService;
import com.lcdt.traffic.service.WaybillPlanService;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.*;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
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
 * Created by yangbinq on 2017/12/13.
 */

@RestController
@RequestMapping("/api/wabillplan")
@Api(value = "计划",description = "计划接口")
public class WaybillPlanApi {

    @Autowired
    private WaybillPlan4CreateService waybillPlan4CreateService; //计划创建

    @Autowired
    private WaybillPlan4EditService waybillPlan4EditService; //计划编辑

    @Autowired
    private WaybillPlanService waybillPlanService;


    @ApiOperation("创建--发布")
    @RequestMapping(value = "/createWaybillPlan",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_create_waybill_plan')")
    public WaybillPlan createWaybillPlan(@RequestBody WaybillParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setPlanSource(ConstantVO.PLAN_SOURCE_ENTERING); //计划来源-录入
        return waybillPlan4CreateService.createWaybillPlan(dto, (short) 1);
   }


    @ApiOperation("创建--暂存计划")
    @RequestMapping(value = "/storageWaybillPlan",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_storage_waybill_plan')")
    public WaybillPlan storageWaybillPlan(@Validated WaybillParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setPlanSource(ConstantVO.PLAN_SOURCE_ENTERING);//计划来源-录入
        return  waybillPlan4CreateService.createWaybillPlan(dto, (short) 2);
    }



    @ApiOperation("编辑--发布")
    @RequestMapping(value = "/waybillPlanEdit4Publish",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_plan_edit_4_publish')")
    public WaybillPlan waybillPlanEdit4Publish(@RequestBody WaybillParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setPlanSource(ConstantVO.PLAN_SOURCE_ENTERING); //计划来源-录入
        return waybillPlan4EditService.waybillPlanEdit(dto, (short) 1);
    }


    @ApiOperation("编辑--暂存")
    @RequestMapping(value = "/waybillPlanEdit4Storage",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_plan_edit_4_storage')")
    public WaybillPlan waybillPlanEdit4Storage(@RequestBody WaybillParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setPlanSource(ConstantVO.PLAN_SOURCE_ENTERING);
        return waybillPlan4EditService.waybillPlanEdit(dto, (short) 2);
    }



    @ApiOperation("列表--发布")
    @RequestMapping(value = "/publishWaybillPlan",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_publish_waybill_plan')")
    public WaybillPlan publishWaybillPlan(@RequestBody WaybillParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        return waybillPlanService.publishWayBillPlan(dto);
    }


    @ApiOperation("计划审核通过")
    @RequestMapping(value = "/waybillPlanCheckPass",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_plan_check_pass')")
    public WaybillPlan waybillPlanCheckPass(@RequestBody WaybillParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        try {
            return waybillPlanService.wayBillPlanCheckPass(dto);
        } catch (WaybillPlanException e) {
            throw new WaybillPlanException(e.getMessage());
        }

    }


    @ApiOperation("拉取计划信息-编辑")
    @RequestMapping(value = "/loadWaybillPlan",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_load_waybill_plan')")
    public WaybillPlan loadWaybillPlan(@ApiParam(value = "计划ID",required = true) @RequestParam Long waybillPlanId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        WaybillParamsDto dto = new WaybillParamsDto();
        dto.setCompanyId(companyId);
        dto.setWaybillPlanId(waybillPlanId);
        try {
            WaybillPlan waybillPlan = waybillPlanService.loadWaybillPlan(dto);
            return  waybillPlan;
        } catch (WaybillPlanException e) {
            throw new WaybillPlanException(e.getMessage());
        }
    }




    @ApiOperation("计划详细删除")
    @RequestMapping(value = "/",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_plan_detail_delete')")
    public String planDetailDelete(@ApiParam(value = "计划商品ID",required = true) @RequestParam Long planDetailId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        JSONObject jsonObject = new JSONObject();
        Integer flag = waybillPlanService.planDetailDelete(planDetailId,companyId);
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



    @ApiOperation("我的计划-列表")
    @RequestMapping(value = "/myPlanList",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_my_waybill_plan_list')")
    public PageBaseDto myPlanList(@Validated WaybillPlanListParamsDto dto,
                             @ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                             @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {
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
            for(int i=0;i<groupList.size();i++) {
                Group group = groupList.get(i);
                sb.append(" find_in_set('"+group.getGroupId()+"',group_id)");
                if(i!=groupList.size()-1){
                    sb.append(" or ");
                }
            }
            sb.append(")");
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
            map.put("pubdateBegin",dto.getPubdateBegin());
        }
        if (StringUtil.isNotEmpty(dto.getPubdateEnd())) {
            map.put("pubdateEnd",dto.getPubdateEnd());
        }
        if (StringUtil.isNotEmpty(dto.getGoodsInfo())) {
            map.put("goodsInfo",dto.getGoodsInfo());
        }
        PageInfo pageInfo = waybillPlanService.wayBillPlanList(map);
        PageBaseDto dto1 = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto1;
    }



    @ApiOperation("留言-列表")
    @RequestMapping(value = "/planLeaveMsgList",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_plan_leave_msg_list')")
    public PageBaseDto planLeaveMsgList( @ApiParam(value = "计划ID",required = true) @RequestParam Long waybillPlanId,
                                                   @ApiParam(value = "创建计划企业ID",required = true) @RequestParam Long createCompanyId,
                                                   @ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                                                   @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("createCompanyId",createCompanyId); //创建计划企业ID
        map.put("waybillPlanId", waybillPlanId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);
        map.put("isDeleted",0); //未删除的
        PageInfo pageInfo = waybillPlanService.planLeaveMsgList(map);
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_plan_leave_msg_Add')")
    public PlanLeaveMsg planLeaveMsgAdd(@ApiParam(value = "计划ID",required = true) @RequestParam Long waybillPlanId,
                                        @ApiParam(value = "创建计划企业ID",required = true) @RequestParam Long companyId,
                                        @ApiParam(value = "留言内容",required = true) @RequestParam String leaveMsg) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        User loginUser = SecurityInfoGetter.getUser();
        PlanLeaveMsgParamsDto dto = new PlanLeaveMsgParamsDto();
        dto.setCompanyId(userCompRel.getCompId());
        dto.setCompanyName(userCompRel.getFullName());
        dto.setUserId(loginUser.getUserId());
        dto.setRealName(loginUser.getRealName());
        dto.setLeaveMsg(leaveMsg);
        dto.setWaybillPlanId(waybillPlanId);
        dto.setCreateCompanyId(companyId); //创建计划企业ID
        try {
            return waybillPlanService.planLeaveMsgAdd(dto);
        } catch (WaybillPlanException e) {
            throw new WaybillPlanException(e.getMessage());
        }
    }







}


