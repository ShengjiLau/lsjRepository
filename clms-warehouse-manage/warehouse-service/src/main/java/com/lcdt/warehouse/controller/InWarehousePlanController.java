package com.lcdt.warehouse.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.Group;
import com.lcdt.warehouse.dto.InHouseParams4SearchDto;
import com.lcdt.warehouse.dto.PageBaseDto;
import com.lcdt.warehouse.entity.InHousePlan;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.service.InWarehousePlanService;
import com.lcdt.warehouse.utils.DateUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tl.commons.util.DateUtility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2018/05/08.
 * Desciption: 仓储入库计划API
 */
@Controller
@RequestMapping("/wh/in/plan")
public class InWarehousePlanController {


    @Autowired
    private InWarehousePlanService inWarehousePlanService;

    @ApiOperation("入库计划列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
    public PageBaseDto inPlanList(@Validated InHouseParams4SearchDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        dto.setCompanyId(companyId);
        if (dto.getCreateBegin()>0) {
            dto.setCreateBeginStr(DateUtils.stampToDate(dto.getCreateBegin()));
        }
        if (dto.getCreateEnd()>0) {
            dto.setCreateEndStr(DateUtils.stampToDate(dto.getCreateEnd()));
        }
        Page pg = inWarehousePlanService.inWarehousePlanList(dto,new Page<InWarehousePlan>(dto.getPageNo(), dto.getPageSize()));
        PageBaseDto result = new PageBaseDto(pg.getRecords(),pg.getTotal());
        return result;
    }

}

