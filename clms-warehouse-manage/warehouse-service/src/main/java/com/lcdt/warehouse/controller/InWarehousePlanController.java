package com.lcdt.warehouse.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.warehouse.dto.InWhPlanSearchParamsDto;
import com.lcdt.warehouse.dto.PageBaseDto;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.service.InWarehousePlanService;
import com.lcdt.warehouse.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yangbinq on 2018/05/08.
 * Desciption: 仓储入库计划API
 */
@RestController
@RequestMapping("/in/plan")
@Api(value = "仓储入库计划API",description = "仓储入库计划API接口")
public class InWarehousePlanController {


    @Autowired
    private InWarehousePlanService inWarehousePlanService;

    @ApiOperation("入库计划列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
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
        Page pg = inWarehousePlanService.inWarehousePlanList(dto,new Page<InWarehousePlan>(dto.getPageNo(), dto.getPageSize()));
        List<InWarehousePlan> inWarehousePlanList = pg.getRecords();
        PageBaseDto result = new PageBaseDto(inWarehousePlanList,pg.getTotal());
        return result;
    }




    @ApiOperation("发布")
    @RequestMapping(value = "/publish",method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
    public PageBaseDto publish(@Validated InWhPlanSearchParamsDto dto) {
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

        Page pg = inWarehousePlanService.inWarehousePlanList(dto,new Page<InWarehousePlan>(dto.getPageNo(), dto.getPageSize()));

        List<InWarehousePlan> inWarehousePlanList = pg.getRecords();
        if (null != inWarehousePlanList) {

        }

        PageBaseDto result = new PageBaseDto(inWarehousePlanList,pg.getTotal());
        return result;
    }



}

