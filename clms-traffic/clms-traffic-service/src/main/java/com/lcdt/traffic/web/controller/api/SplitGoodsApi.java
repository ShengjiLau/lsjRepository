package com.lcdt.traffic.web.controller.api;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.model.SplitGoods;
import com.lcdt.traffic.service.SplitGoodsService;
import com.lcdt.traffic.web.dto.SplitGoodsParamsDto;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yangbinq on 2017/12/21.
 */

@RestController
@RequestMapping("/api/splitoods")
@Api(value = "派单",description = "派单接口")
public class SplitGoodsApi {


    @Autowired
    private SplitGoodsService splitGoodsService;



    @ApiOperation("派单-信息拉取")
    @RequestMapping(value = "/splitGoodsLoad",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_split_goods_load')")
    public String splitGoodsLoad(@ApiParam(value = "计划ID",required = true) @RequestParam Long waybillPlanId) {

        return  null;
    }

    @ApiOperation(value = "派单-直派")
    @RequestMapping(value = "/splitGoods4Direct",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_split_goods_4_direct')")
    public String splitGoods4Direct(@ApiParam(value = "派单详细信息", required = true) @RequestBody SplitGoodsParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        splitGoodsService.splitGoods4Direct(dto,loginUser,companyId);
        return null;
    }

}
