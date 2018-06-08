package com.lcdt.warehouse.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.warehouse.dto.InWhPlanDto;
import com.lcdt.warehouse.dto.InWhPlanSearchParamsDto;
import com.lcdt.warehouse.dto.PageBaseDto;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.service.InWarehousePlanService;
import com.lcdt.warehouse.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yangbinq on 2018/05/08.
 * Desciption: 仓储入库计划API
 */
@RestController
@RequestMapping("/in/plan")
@Api(value = "仓储入库计划API",description = "仓储入库计划API接口")
public class InWarehousePlanController {

    private static Logger logger = LoggerFactory.getLogger(InWarehousePlanController.class);

    @Autowired
    private InWarehousePlanService inWarehousePlanService;

    @ApiOperation("入库计划列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_plan_search')")
    public PageBaseDto inPlanList(@Validated InWhPlanSearchParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        dto.setCompanyId(companyId);
        if (dto.getCreateBegin()!=null && dto.getCreateBegin()>0) {
            dto.setCreateBeginStr(DateUtils.stampToDate(dto.getCreateBegin()));
        }
        if (dto.getCreateBegin()!=null && dto.getCreateEnd()>0) {
            dto.setCreateEndStr(DateUtils.stampToDate(dto.getCreateEnd()));
        }
        if(!StringUtils.isEmpty(dto.getPlanStatus()) && dto.getPlanStatus().equals("00")) {
            dto.setPlanStatus(null); //查询所有
        }
        if(StringUtils.isEmpty(dto.getGroupId())) {
            StringBuffer sb = new StringBuffer();
            List<Group> groupList = SecurityInfoGetter.groups();
            if(groupList!=null && groupList.size()>0) {
                for(int i=0;i<groupList.size();i++) {
                    Group group = groupList.get(i);
                    sb.append(group.getGroupId()+",");
                }
                dto.setGroupIds(sb.toString().substring(0,sb.toString().length()-1));
            }
        } else {
            if (dto.getGroupId()>0) {
                dto.setGroupIds(dto.getGroupId().toString());
            }
        }
        Page pg = inWarehousePlanService.inWarehousePlanList(dto,new Page<InWarehousePlan>(dto.getPageNo(), dto.getPageSize()));
        List<InWarehousePlan> inWarehousePlanList = pg.getRecords();
        PageBaseDto result = new PageBaseDto(inWarehousePlanList,pg.getTotal());
        return result;
    }


    @ApiOperation("发布")
    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_plan_publish')")
    public JSONObject publish(@ApiParam(value = "计划ID",required = true) @RequestParam Long planId) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        InWarehousePlan obj = new InWarehousePlan();
        obj.setPlanId(planId);
        JSONObject jsonObject = new JSONObject();
        boolean flag = false;
        String msg = "发布失败！";
        try {
            flag = inWarehousePlanService.inWhPlanPublish(obj, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "发布成功！":msg);
        return jsonObject;
    }


    @ApiOperation("取消")
    @RequestMapping(value = "/cancel",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_plan_cancel')")
    public JSONObject cancel(@ApiParam(value = "计划ID",required = true) @RequestParam Long planId) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        InWarehousePlan obj = new InWarehousePlan();
        obj.setPlanId(planId);
        JSONObject jsonObject = new JSONObject();
        boolean flag = false;
        String msg = "取消失败！";
        try {
            flag = inWarehousePlanService.inWhPlanCancel(obj, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "取消成功！":msg);
        return jsonObject;
    }



    @ApiOperation("完成")
    @RequestMapping(value = "/complete",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_plan_complete')")
    public JSONObject complete(@ApiParam(value = "计划ID",required = true) @RequestParam Long planId) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        InWarehousePlan obj = new InWarehousePlan();
        obj.setPlanId(planId);
        JSONObject jsonObject = new JSONObject();
        boolean flag = false;
        String msg = "操作失败！";
        try {
            flag = inWarehousePlanService.inWhPlanComplete(obj, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "操作成功！":msg);
        return jsonObject;
    }



    @ApiOperation("计划保存")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_plan_add')")
    public JSONObject add(@RequestBody InWhPlanDto inWhPlanAddParamsDto) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        String msg = "新建失败！";
        boolean flag = false;
        JSONObject jsonObject = new JSONObject();
        try {
            flag = inWarehousePlanService.inWhPlanAdd(inWhPlanAddParamsDto, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "新建成功！":msg);
        return jsonObject;
    }



    @ApiOperation("计划详细")
    @RequestMapping(value = "/detail",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_plan_detail')")
    public JSONObject detail(@ApiParam(value = "计划ID",required = true) @RequestParam Long planId,
                             @ApiParam(value = "是否加载配仓",required = true) @RequestParam boolean flag,
                             @ApiParam(value = "加载配仓完成后，是否显示",required = true) @RequestParam boolean flag1) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        JSONObject jsonObject = new JSONObject();
        InWhPlanDto inWhPlanDto = inWarehousePlanService.inWhPlanDetail(planId,flag, userCompRel,false,flag1);
        jsonObject.put("data",inWhPlanDto);
        jsonObject.put("code", 0);
        return jsonObject;
    }



    @ApiOperation("计划编辑")
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_plan_edit')")
    public JSONObject edit(@RequestBody InWhPlanDto inWhPlanAddParamsDto) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        String msg = "编辑失败！";
        boolean flag = false;
        JSONObject jsonObject = new JSONObject();
        try {
            flag = inWarehousePlanService.inWhPlanEdit(inWhPlanAddParamsDto, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "编辑成功！":msg);
        return jsonObject;
    }


    @ApiOperation("计划配仓")
    @RequestMapping(value = "/distributeWh",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_plan_dis')")
    public JSONObject distributeWh(@RequestBody InWhPlanDto inWhPlanAddParamsDto) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        String msg = "配仓失败！";
        boolean flag = false;
        JSONObject jsonObject = new JSONObject();
        try {
            flag = inWarehousePlanService.distributeWh(inWhPlanAddParamsDto, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "配仓成功！":msg);
        return jsonObject;
    }


}

