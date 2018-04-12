package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.traffic.dto.ReceivePayParamsDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by yangbinq on 2018/4/12.
 */
@RestController
@RequestMapping("/finace")
@Api(value = "财务统计",description = "财务统计")
public class FinanceApi {


    @ApiOperation("应收统计")
    @RequestMapping(value = "/receive/stat",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
    public JSONObject receiveStat(ReceivePayParamsDto dto) {




        return null;
    }

}
