package com.lcdt.warehouse.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.warehouse.dto.InWhPlanDto;
import com.lcdt.warehouse.dto.OutWhPlanDto;
import com.lcdt.warehouse.dto.OutWhPlanSearchParamsDto;
import com.lcdt.warehouse.dto.PageBaseDto;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.entity.OutWarehousePlan;
import com.lcdt.warehouse.service.OutWarehousePlanService;
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

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 * @Desciption: 仓储出库计划API
 */
@RestController
@RequestMapping("/out/plan")
@Api(value = "仓储出库计划API",description = "仓储出库计划API接口")
public class OutWarehousePlanController {

    private static Logger logger = LoggerFactory.getLogger(InWarehousePlanController.class);
    @Autowired

    private OutWarehousePlanService outWarehousePlanService;

    @ApiOperation("出库计划列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN')  or hasAuthority('wh_out_plan_search')")
    public PageBaseDto inPlanList(@Validated OutWhPlanSearchParamsDto dto) {
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
        Page pg = outWarehousePlanService.outWarehousePlanList(dto,new Page<OutWarehousePlan>(dto.getPageNo(), dto.getPageSize()));
        List<InWarehousePlan> inWarehousePlanList = pg.getRecords();
        PageBaseDto result = new PageBaseDto(inWarehousePlanList,pg.getTotal());
        return result;
    }


    @ApiOperation("计划详细")
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_plan_detail')")
    public OutWhPlanDto detail(@ApiParam(value = "计划ID",required = true) @RequestParam Long outPlanId,
                             @ApiParam(value = "是否加载配仓",required = true) @RequestParam boolean flag) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        JSONObject jsonObject = new JSONObject();
        OutWhPlanDto outWhPlanDto = outWarehousePlanService.outWhPlanDetail(outPlanId,flag, userCompRel);
        return outWhPlanDto;
    }



    @ApiOperation("计划保存")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_plan_add')")
    public JSONObject add(@RequestBody OutWhPlanDto outWhPlanDto) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        String msg = "新建失败！";
        OutWarehousePlan outWarehousePlan = null;
        JSONObject jsonObject = new JSONObject();
        try {
            outWarehousePlan = outWarehousePlanService.outWhPlanAdd(outWhPlanDto, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", outWarehousePlan==null? -1:0);
        jsonObject.put("message", outWarehousePlan==null? msg:"新建成功！");
        return jsonObject;
    }


    @ApiOperation("计划编辑")
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_plan_edit')")
    public JSONObject edit(@RequestBody OutWhPlanDto outWhPlanDto) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        String msg = "编辑失败！";
        boolean flag = false;
        JSONObject jsonObject = new JSONObject();
        try {
            flag = outWarehousePlanService.outWhPlanEdit(outWhPlanDto, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "编辑成功！":msg);
        return jsonObject;
    }




    @ApiOperation("发布")
    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_plan_publish')")
    public JSONObject publish(@ApiParam(value = "计划ID",required = true) @RequestParam Long planOutId) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        OutWarehousePlan obj = new OutWarehousePlan();
        obj.setOutplanId(planOutId);
        JSONObject jsonObject = new JSONObject();
        boolean flag = false;
        String msg = "发布失败！";
        try {
            flag = outWarehousePlanService.outWhPlanPublish(obj, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "发布成功 ！":msg);
        return jsonObject;
    }




    @ApiOperation("完成")
    @RequestMapping(value = "/complete",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_plan_complete')")
    public JSONObject complete(@ApiParam(value = "计划ID",required = true) @RequestParam Long planOutId) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        OutWarehousePlan obj = new OutWarehousePlan();
        obj.setOutplanId(planOutId);
        JSONObject jsonObject = new JSONObject();
        boolean flag = false;
        String msg = "操作失败！";
        try {
            flag = outWarehousePlanService.outWhPlanComplete(obj, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "操作成功！":msg);
        return jsonObject;
    }


    @ApiOperation("取消")
    @RequestMapping(value = "/cancel",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_plan_cancel')")
    public JSONObject cancel(@ApiParam(value = "计划ID",required = true) @RequestParam Long planOutId) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        OutWarehousePlan obj = new OutWarehousePlan();
        obj.setOutplanId(planOutId);
        JSONObject jsonObject = new JSONObject();
        boolean flag = false;
        String msg = "取消失败！";
        try {
            flag = outWarehousePlanService.outWhPlanCancel(obj, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "取消成功！":msg);
        return jsonObject;
    }


    @ApiOperation("计划配仓")
    @RequestMapping(value = "/distributeWh",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_plan_dis')")
    public JSONObject distributeWh(@RequestBody OutWhPlanDto outWhPlanDto) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        String msg = "配仓失败！";
        boolean flag = false;
        JSONObject jsonObject = new JSONObject();
        try {
           flag = outWarehousePlanService.distributeWh(outWhPlanDto, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "配仓成功！":msg);
        return jsonObject;
    }


}

