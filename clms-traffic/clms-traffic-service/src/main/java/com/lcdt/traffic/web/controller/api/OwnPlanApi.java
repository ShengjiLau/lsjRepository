package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.quartz.service.IQuartzService;
import com.lcdt.traffic.exception.WaybillPlanException;
import com.lcdt.traffic.job.TestJob;
import com.lcdt.traffic.job.TestJob1;
import com.lcdt.traffic.model.PlanLeaveMsg;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.service.Plan4CreateService;
import com.lcdt.traffic.service.Plan4EditService;
import com.lcdt.traffic.service.PlanService;
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
    private IQuartzService iQuartzService;



    @ApiOperation("创建--发布")
    @RequestMapping(value = "/createPlan",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_create_plan')")
    public JSONObject createPlan(@RequestBody WaybillParamsDto dto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
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


    @ApiOperation("创建--暂存计划")
    @RequestMapping(value = "/storagePlan",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_storage_plan')")
    public JSONObject storagePlan(@RequestBody WaybillParamsDto dto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setPlanSource(ConstantVO.PLAN_SOURCE_ENTERING);//计划来源-录入
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        plan4CreateService.createWaybillPlan(dto, (short) 2);
        jsonObject.put("code", 0);
        jsonObject.put("message", "暂存成功！");
        return jsonObject;
    }



    @ApiOperation("编辑--发布")
    @RequestMapping(value = "/planEdit4Publish",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_plan_edit_4_publish')")
    public JSONObject planEdit4Publish(@RequestBody WaybillParamsDto dto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setPlanSource(ConstantVO.PLAN_SOURCE_ENTERING); //计划来源-录入
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        plan4EditService.waybillPlanEdit(dto, (short) 1);
        jsonObject.put("code", 0);
        jsonObject.put("message", "发布成功！");
        return jsonObject;

    }


    @ApiOperation("编辑--暂存")
    @RequestMapping(value = "/planEdit4Storage",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_plan_edit_4_storage')")
    public JSONObject planEdit4Storage(@RequestBody WaybillParamsDto dto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setPlanSource(ConstantVO.PLAN_SOURCE_ENTERING);

        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        plan4EditService.waybillPlanEdit(dto, (short) 2);
        jsonObject.put("code", 0);
        jsonObject.put("message", "暂存成功！");
        return jsonObject;
    }



    @ApiOperation("列表--发布")
    @RequestMapping(value = "/publishPlan",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_publish_plan')")
    public JSONObject publishPlan(@RequestBody WaybillParamsDto dto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());

        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        planService.publishWayBillPlan(dto);
        jsonObject.put("code", 0);
        jsonObject.put("message", "发布成功！");
        return jsonObject;
    }


    @ApiOperation("计划审核通过")
    @RequestMapping(value = "/planCheckPass",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_plan_check_pass')")
    public JSONObject planCheckPass(@ApiParam(value = "计划ID",required = true) @RequestParam Long waybillPlanId) {
        WaybillParamsDto dto = new WaybillParamsDto();
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setWaybillPlanId(waybillPlanId);
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        JSONObject jsonObject = new JSONObject();
        WaybillPlan waybillPlan =  planService.wayBillPlanCheckPass(dto);
        String message = "审批成功！";
        int code = -1;
        if (waybillPlan!=null && waybillPlan.getIsApproval()==2) {
            code = 0;
        } else {
            message = "审批失败，请重试！";
        }
        jsonObject.put("message",message);
        jsonObject.put("code",code);
        return jsonObject;
    }



    @ApiOperation("拉取计划信息-编辑")
    @RequestMapping(value = "/loadPlan",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_load_plan')")
    public WaybillPlan loadPlan(@ApiParam(value = "计划ID",required = true) @RequestParam Long waybillPlanId) {
        iQuartzService.deleteJob("job_product_pull1", "jGroup_product_pull1");
        iQuartzService.startSchedule("job_product_pull1", "jGroup_product_pull1", "0/5 * * * * ?", "trigger_product_pull1",
                "tGroup__product_pull1", TestJob1.class);
        iQuartzService.test();
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
            sb.append("(");
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
        PageInfo pageInfo = planService.wayBillPlanList(map);
        PageBaseDto dto1 = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto1;
    }

    @ApiOperation("留言-列表")
    @RequestMapping(value = "/planLeaveMsgList",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_plan_leave_msg_list')")
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_plan_leave_msg_Add')")
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





}


