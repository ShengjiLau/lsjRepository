package com.lcdt.traffic.web.controller.api;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.WaybillParamsDto;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangbinq on 2017/12/13.
 */

@RestController
@RequestMapping("/api/waybill")
@Api(value = "运单计划",description = "运单计划接口")
public class WaybillPlanApi {

    @ApiOperation("发布运单计划")
    @RequestMapping(value = "/publishWaybillPlan",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_publish_waybill_plan')")
    public void publishWaybillPlan(@Validated WaybillParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setPlanSource(ConstantVO.PLAN_SOURCE_ENTERING); //计划来源-录入







    }



}


