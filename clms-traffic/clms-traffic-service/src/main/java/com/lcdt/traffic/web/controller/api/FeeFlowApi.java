package com.lcdt.traffic.web.controller.api;

import com.lcdt.traffic.dto.FeeFlow4SearchParamsDto;
import com.lcdt.traffic.web.dto.PageBaseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangbinq on 2018/4/12.
 */
@RestController
@RequestMapping("/fee/flow")
@Api(value = "记账流水",description = "记账流水")
public class FeeFlowApi {




    @ApiOperation("应收/应付列表")
    @RequestMapping(value = "/search/list",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
    public PageBaseDto searchList(FeeFlow4SearchParamsDto dto,
                                                @ApiParam(value = "页码",required = true,defaultValue = "1") @RequestParam Integer pageNo,
                                                @ApiParam(value = "每页显示条数",required = true,defaultValue = "10") @RequestParam Integer pageSize) {


        PageBaseDto pg_result = new PageBaseDto(null, 1);
        return pg_result;
    }


}
